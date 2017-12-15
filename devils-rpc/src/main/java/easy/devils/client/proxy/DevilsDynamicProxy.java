package easy.devils.client.proxy;

import easy.devils.annotation.MethodProvider;
import easy.devils.annotation.ServiceProvider;
import easy.devils.client.cluster.FailoverCheckManager;
import easy.devils.client.cluster.ha.AbstractHaStrategy;
import easy.devils.client.cluster.ClusterBuilder;
import easy.devils.client.cluster.loadbalance.AbstractLoadBalance;
import easy.devils.codec.DevilsHeader;
import easy.devils.codec.DevilsMessage;
import easy.devils.codec.DevilsRequest;
import easy.devils.common.DevilsConstant;
import easy.devils.config.MethodProviderConfig;
import easy.devils.config.ServiceProviderConfig;
import easy.devils.discovery.AbstractServiceDiscovery;
import easy.devils.discovery.IServiceDiscovery;
import easy.devils.discovery.impl.LocalServiceDiscovery;
import easy.devils.exception.DevilsExceptionConstant;
import easy.devils.exception.DevilsFrameworkException;
import easy.devils.processor.AbstractAnnotationProcessorProvider;
import easy.devils.processor.IAnnotationProcessor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Map;

/**
 * 注解 < xml配置
 * @author limengyu
 * @create 2017/12/12
 */
public class DevilsDynamicProxy implements InvocationHandler{

    private static final Logger LOGGER = LoggerFactory.getLogger(DevilsDynamicProxy.class);
    private static final AbstractAnnotationProcessorProvider DEFAULT_PROVIDER = AbstractAnnotationProcessorProvider.DEFAULT_PROVIDER;
    private Class<?> serviceInterface;
    private AbstractServiceDiscovery serviceDiscovery;
    private ServiceProviderConfig serviceProviderConfig;
    private String serviceName;
    private AbstractHaStrategy haStrategy;
    private AbstractLoadBalance loadBalance;
    private GenericKeyedObjectPoolConfig config;

    public DevilsDynamicProxy(Class<?> serviceInterface,
                              ServiceProviderConfig providerConfig,
                              AbstractServiceDiscovery serviceDiscovery,
                              FailoverCheckManager checkManager,
                              GenericKeyedObjectPoolConfig poolConfig){

        this.serviceInterface = serviceInterface;
        this.serviceDiscovery = serviceDiscovery == null ? new LocalServiceDiscovery<>() : serviceDiscovery;
        this.config = poolConfig;
        this.loadDefaultConfig();
        this.coverDefaultConfig(providerConfig);
        this.haStrategy = ClusterBuilder.builderHaStrategy(serviceProviderConfig.getHaStrategyType(),config);
        this.loadBalance = ClusterBuilder.builderLoadBalance(serviceProviderConfig.getLoadBalanceType(), serviceDiscovery, this.serviceName, checkManager);
        if(this.serviceDiscovery instanceof LocalServiceDiscovery){
            ((LocalServiceDiscovery) serviceDiscovery).register(serviceName);
        }
        //订阅事件,服务下线时清除链接
        this.serviceDiscovery.subscribeService(serviceName,haStrategy);
    }

    public static <T> T newProxyInstance(Class<T> interfaceClass,ServiceProviderConfig providerConfig,AbstractServiceDiscovery discovery,FailoverCheckManager checkManager){
        return (T)Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                new Class[]{interfaceClass},
                new DevilsDynamicProxy(interfaceClass, providerConfig, discovery, checkManager,new GenericKeyedObjectPoolConfig())
        );
    }

    public static <T> T newProxyInstance(Class<T> interfaceClass,ServiceProviderConfig providerConfig,AbstractServiceDiscovery discovery,FailoverCheckManager checkManager,GenericKeyedObjectPoolConfig config){
        return (T)Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                new Class[]{interfaceClass},
                new DevilsDynamicProxy(interfaceClass, providerConfig, discovery, checkManager,config)
        );
    }


    /**
     * 读取服务接口上的配置信息作为默认配置
     * @ServiceProvider
     * @MethodProvider
     */
    public void loadDefaultConfig(){
        ServiceProvider defaultServiceProvider = this.serviceInterface.getDeclaredAnnotation(ServiceProvider.class);
        if(defaultServiceProvider == null){
            throw new DevilsFrameworkException("serviceInterface missing @ServiceProvider annotation", DevilsExceptionConstant.SERVICE_DEFAULT_ERROR);
        }
        this.serviceName = defaultServiceProvider.serviceName();
        this.serviceProviderConfig = ServiceProviderConfig.Builder.createBuilder()
                .setServiceName(this.serviceName)
                .setHaStrategyType(defaultServiceProvider.haStrategyType())
                .setLoadBalanceType(defaultServiceProvider.loadBalanceType())
                .setGroup(defaultServiceProvider.group())
                .setWeight(defaultServiceProvider.weight())
                .setMaxThreadNum(defaultServiceProvider.maxThreadNum())
                .setConnectionTimeOut(defaultServiceProvider.connectionTimeOut())
                .build();
        List<IAnnotationProcessor> processorList = DEFAULT_PROVIDER.getProcessorList();
        Method[] methods = serviceInterface.getDeclaredMethods();
        for (Method method : methods) {
            for (IAnnotationProcessor processor : processorList) {
                if (processor.accept(serviceInterface,method)){
                    processor.process(serviceInterface,method,serviceProviderConfig);
                }
            }
        }
    }

    /**
     * 覆盖默认配置
     * @param providerConfig
     */
    public void coverDefaultConfig(ServiceProviderConfig providerConfig){
        if(providerConfig == null){
            return;
        }
        if(providerConfig.getHaStrategyType() != null){
            this.serviceProviderConfig.setHaStrategyType(providerConfig.getHaStrategyType());
        }
        if(providerConfig.getLoadBalanceType() != null){
            this.serviceProviderConfig.setLoadBalanceType(providerConfig.getLoadBalanceType());
        }
        if(StringUtils.isNotBlank(providerConfig.getGroup())){
            this.serviceProviderConfig.setGroup(providerConfig.getGroup());
        }
        if(providerConfig.getHost() != null && providerConfig.getHost().size() > 0){
            this.serviceProviderConfig.setHost(providerConfig.getHost());
        }
        if(providerConfig.getWeight() > 0){
            this.serviceProviderConfig.setWeight(providerConfig.getWeight() > DevilsConstant.WEIGHT_MAX ? DevilsConstant.WEIGHT_MAX : providerConfig.getWeight());
        }
        Map<String, MethodProviderConfig> methodConfigMap = providerConfig.getMethodConfigMap();
        if(methodConfigMap != null && methodConfigMap.size() > 0){
            methodConfigMap.forEach((methodName, methodConfig) -> {
                MethodProviderConfig config = this.serviceProviderConfig.getMethodConfig(methodName);
                if(config != null){
                    methodConfig.setServiceName(config.getServiceName());
                    methodConfig.setMethodName(config.getMethodName());
                    this.serviceProviderConfig.addMethod(methodName,methodConfig);
                }
            });
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        MethodProvider methodProvider = method.getDeclaredAnnotation(MethodProvider.class);
        if(methodProvider == null){
            LOGGER.warn("method [{}] not found @MethodProvider",method.getName());
            return null;
        }
        String methodName = StringUtils.isBlank(methodProvider.methodName()) ? method.getName() : methodProvider.methodName();
        MethodProviderConfig methodConfig = serviceProviderConfig.getMethodConfig(methodName);
        //构造请求
        DevilsHeader devilsHeader = DevilsHeader.Builder.createBuilder()
                .loadFromConfig(methodConfig)
                .builder();
        DevilsRequest request = new DevilsRequest(serviceName,methodName,args);
        DevilsMessage<DevilsRequest> message = new DevilsMessage<>(devilsHeader,request);
        return haStrategy.call(message,loadBalance);
    }

    public Class<?> getServiceInterface() {
        return serviceInterface;
    }

    public void setServiceInterface(Class<?> serviceInterface) {
        this.serviceInterface = serviceInterface;
    }

    public IServiceDiscovery getServiceDiscovery() {
        return serviceDiscovery;
    }

    public void setServiceDiscovery(AbstractServiceDiscovery serviceDiscovery) {
        this.serviceDiscovery = serviceDiscovery;
    }

    public ServiceProviderConfig getServiceProviderConfig() {
        return serviceProviderConfig;
    }

    public void setServiceProviderConfig(ServiceProviderConfig serviceProviderConfig) {
        this.serviceProviderConfig = serviceProviderConfig;
    }
}
