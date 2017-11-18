package easy.devils.discovery.impl;

import easy.devils.discovery.AbstractServiceDiscovery;
import easy.devils.discovery.AbstractServiceEventListener;
import org.apache.curator.x.discovery.ServiceInstance;

import java.util.Collection;

/**
 * 本地Map服务注册中心
 * @author limengyu
 * @create 2017/11/18
 */
public class LocalServiceDiscovery extends AbstractServiceDiscovery {
    @Override
    public void start() {

    }

    @Override
    public void registerService(ServiceInstance serviceInstance) {

    }

    @Override
    public void unregisterService(ServiceInstance serviceInstance) {

    }

    @Override
    public void updateServive(ServiceInstance serviceInstance) {

    }

    @Override
    public void subscribeService(AbstractServiceEventListener eventListener) {

    }

    @Override
    public void subscribeServive(String serviceName, AbstractServiceEventListener eventListener) {

    }

    @Override
    public void unSubscribeService(AbstractServiceEventListener eventListener) {

    }

    @Override
    public void unSubscribeService(String serviceName, AbstractServiceEventListener eventListener) {

    }

    @Override
    public Collection<String> queryServiceNameList() {
        return null;
    }

    @Override
    public Collection<ServiceInstance> queryServiceInstance(String serviceName) {
        return null;
    }
}
