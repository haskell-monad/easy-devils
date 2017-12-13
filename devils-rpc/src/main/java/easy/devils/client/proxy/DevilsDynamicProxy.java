package easy.devils.client.proxy;

import easy.devils.annotation.ServiceProvider;
import easy.devils.config.ServiceProviderConfig;
import easy.devils.discovery.IServiceDiscovery;
import easy.devils.exception.DevilsExceptionConstant;
import easy.devils.exception.DevilsFrameworkException;
import easy.devils.processor.AbstractAnnotationProcessorProvider;
import easy.devils.processor.IAnnotationProcessor;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 注解 < xml配置
 * @author limengyu
 * @create 2017/12/12
 */
public class DevilsDynamicProxy implements InvocationHandler{

    private Class<?> serviceInterface;
    private IServiceDiscovery serviceDiscovery;
    private ServiceProviderConfig serviceProviderConfig;
    private String serviceName;
    private static final AbstractAnnotationProcessorProvider DEFAULT_PROVIDER = AbstractAnnotationProcessorProvider.DEFAULT_PROVIDER;

    public DevilsDynamicProxy(Class<?> serviceInterface,ServiceProviderConfig serviceProviderConfig,IServiceDiscovery serviceDiscovery){
        this.serviceInterface = serviceInterface;
        this.serviceDiscovery = serviceDiscovery;
        this.loadDefaultConfig();
        this.coverDefaultConfig(serviceProviderConfig);
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
     * @param serviceProviderConfig
     */
    public void coverDefaultConfig(ServiceProviderConfig serviceProviderConfig){
        if(serviceProviderConfig == null){
            return;
        }
        if(serviceProviderConfig.getHaStrategyType() != null){
            this.serviceProviderConfig.setHaStrategyType(serviceProviderConfig.getHaStrategyType());
        }
        if(serviceProviderConfig.getLoadBalanceType() != null){
            this.serviceProviderConfig.setLoadBalanceType(serviceProviderConfig.getLoadBalanceType());
        }
        if(StringUtils.isNotBlank(serviceProviderConfig.getGroup())){
            this.serviceProviderConfig.setGroup(serviceProviderConfig.getGroup());
        }
        if(StringUtils.isNotBlank(serviceProviderConfig.getGroup())){

        }


    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return null;
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

    public void setServiceDiscovery(IServiceDiscovery serviceDiscovery) {
        this.serviceDiscovery = serviceDiscovery;
    }

    public ServiceProviderConfig getServiceProviderConfig() {
        return serviceProviderConfig;
    }

    public void setServiceProviderConfig(ServiceProviderConfig serviceProviderConfig) {
        this.serviceProviderConfig = serviceProviderConfig;
    }
}
