package easy.devils;

import easy.devils.client.proxy.DevilsDynamicProxy;
import easy.devils.config.ServiceProviderConfig;
import easy.devils.discovery.IServiceDiscovery;

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
    public static Object newProxyInstance(Class<?> interfaceClass,ServiceProviderConfig providerConfig,IServiceDiscovery discovery){
        return Proxy.newProxyInstance(
            interfaceClass.getClassLoader(),
            interfaceClass.getInterfaces(),
            new DevilsDynamicProxy(interfaceClass,providerConfig,discovery)
        );
    }

    public static Object newProxyInstance(){
        return null;
    }
}
