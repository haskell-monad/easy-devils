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
     * @throws Exception
     */
    void registerService(ServiceInstance<T> serviceInstance) throws Exception;

    /**
     * 取消注册服务
     * @param serviceInstance
     * @throws Exception
     */
    void unregisterService(ServiceInstance<T> serviceInstance) throws Exception;

    /**
     * 更新服务
     * @param serviceInstance
     * @throws Exception
     */
    void updateService(ServiceInstance<T> serviceInstance) throws Exception;

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
    void subscribeService(String serviceName,AbstractServiceEventListener<T> eventListener);
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
     * @throws Exception
     */
    Collection<String> queryServiceNameList() throws Exception;

    /**
     * 根据服务名称查询所有服务实例
     * @param serviceName
     * @return
     * @throws Exception
     */
    Collection<ServiceInstance<T>> queryServiceInstance(String serviceName) throws Exception;

    /**
     * 根据服务名称和服务实例id查询实例
     * @param serviceName
     * @param instanceId
     * @return
     * @throws Exception
     */
    ServiceInstance<T> queryServiceInstance(String serviceName,String instanceId) throws Exception;
}
