package easy.devils;

import easy.devils.client.cluster.FailoverCheckManager;
import easy.devils.client.proxy.DevilsDynamicProxy;
import easy.devils.config.ServiceProviderConfig;
import easy.devils.discovery.AbstractServiceDiscovery;
import easy.devils.discovery.IServiceDiscovery;
import easy.devils.discovery.impl.LocalServiceDiscovery;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;

import java.lang.reflect.Proxy;

/**
 * @author limengyu
 * @create 2017/12/12
 */
public class Devils {

    /**
     * 创建服务代理类
     * @param interfaceClass
     * @param providerConfig
     * @param discovery
     */
    public static <T> T newProxyInstance(Class<T> interfaceClass,ServiceProviderConfig providerConfig,AbstractServiceDiscovery discovery){
        return DevilsDynamicProxy.newProxyInstance(interfaceClass,providerConfig,discovery,new FailoverCheckManager());
    }

    public static <T> T newProxyInstance(Class<T> interfaceClass,ServiceProviderConfig providerConfig,AbstractServiceDiscovery discovery,GenericKeyedObjectPoolConfig config){
        return DevilsDynamicProxy.newProxyInstance(interfaceClass,providerConfig,discovery,new FailoverCheckManager(),config);
    }

    public static <T> T newProxyInstance(Class<T> interfaceClass){
        return DevilsDynamicProxy.newProxyInstance(interfaceClass,null,new LocalServiceDiscovery<>(),new FailoverCheckManager(),new GenericKeyedObjectPoolConfig());
    }

    public static <T> T newProxyInstance(Class<T> interfaceClass,ServiceProviderConfig providerConfig){
        return DevilsDynamicProxy.newProxyInstance(interfaceClass,providerConfig,new LocalServiceDiscovery<>(),new FailoverCheckManager(),new GenericKeyedObjectPoolConfig());
    }
}
