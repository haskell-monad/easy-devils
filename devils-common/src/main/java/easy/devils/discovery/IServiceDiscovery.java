package easy.devils.discovery;

import org.apache.curator.x.discovery.ServiceInstance;

import java.util.Collection;

/**
 * 服务注册中心
 * @author limengyu
 * @create 2017/11/18
 */
public interface IServiceDiscovery<T> {
    /**
     * 注册服务
     * @param serviceInstance
     */
    void registerService(ServiceInstance<T> serviceInstance);

    /**
     * 取消注册服务
     * @param serviceInstance
     */
    void unregisterService(ServiceInstance<T> serviceInstance);

    /**
     * 更新服务
     * @param serviceInstance
     */
    void updateServive(ServiceInstance<T> serviceInstance);

    /**
     * 订阅服务
     * @param eventListener
     */
    void subscribeService(AbstractServiceEventListener<T> eventListener);
    /**
     * 订阅服务
     * @param serviceName
     * @param eventListener
     */
    void subscribeServive(String serviceName,AbstractServiceEventListener<T> eventListener);
    /**
     * 取消订阅服务
     * @param eventListener
     */
    void unSubscribeService(AbstractServiceEventListener<T> eventListener);
    /**
     * 取消订阅服务
     * @param serviceName
     * @param eventListener
     */
    void unSubscribeService(String serviceName,AbstractServiceEventListener<T> eventListener);

    /**
     * 查询所有服务名称
     * @return
     */
    Collection<String> queryServiceNameList();

    /**
     * 根据服务名称查询所有服务实例
     * @param serviceName
     * @return
     */
    Collection<ServiceInstance<T>> queryServiceInstance(String serviceName);
}
