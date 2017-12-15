package easy.devils.support.spring;

import easy.devils.Devils;
import easy.devils.config.MethodProviderConfig;
import easy.devils.config.ServiceProviderConfig;
import easy.devils.discovery.AbstractServiceDiscovery;
import easy.devils.discovery.IServiceDiscovery;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 服务接口代理
 * @author limengyu
 * @create 2017/11/23
 */
public class DevilsProxyFactoryBean implements FactoryBean<Object>,InitializingBean,DisposableBean{

    /**
     * 服务接口
     */
    private Class<?> serviceInterface;
    /**
     * 暴露的服务接口方法配置信息
     */
    private Map<String,MethodProviderConfig> methodProviderConfigMap;
    /**
     * 注册中心
     */
    private AbstractServiceDiscovery registry;
    /**
     * 生成的代理对象
     */
    private Object proxy;

    @Override
    public void destroy() throws Exception {

    }

    @Override
    public Object getObject() throws Exception {
        return proxy;
    }

    @Override
    public Class<?> getObjectType() {
        return serviceInterface;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        ServiceProviderConfig serviceProviderConfig = ServiceProviderConfig.Builder.createBuilder().build();
        for (Map.Entry<String, MethodProviderConfig> methodProvider : methodProviderConfigMap.entrySet()) {
            serviceProviderConfig.addMethod(methodProvider.getKey(),methodProvider.getValue());
        }
        //创建代理类
        this.proxy = Devils.newProxyInstance(serviceInterface,serviceProviderConfig,registry);
    }

    public Class<?> getServiceInterface() {
        return serviceInterface;
    }

    public void setServiceInterface(Class<?> serviceInterface) {
        this.serviceInterface = serviceInterface;
    }

    public Map<String, MethodProviderConfig> getMethodProviderConfigMap() {
        return methodProviderConfigMap;
    }

    public void setMethodProviderConfigMap(Map<String, MethodProviderConfig> methodProviderConfigMap) {
        this.methodProviderConfigMap = methodProviderConfigMap;
    }

    public IServiceDiscovery getRegistry() {
        return registry;
    }

    public void setRegistry(AbstractServiceDiscovery registry) {
        this.registry = registry;
    }
}
