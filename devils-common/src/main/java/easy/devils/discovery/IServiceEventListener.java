package easy.devils.discovery;

import org.apache.curator.x.discovery.ServiceInstance;

/**
 * 服务事件监听器
 * @author limengyu
 * @create 2017/11/18
 */
public interface IServiceEventListener<T> {

    /**
     * 注册服务事件
     * @param serviceInstance
     */
    void onRegister(ServiceInstance<T> serviceInstance);

    /**
     * 取消注册服务事件
     * @param serviceInstance
     */
    void onUnRegister(ServiceInstance<T> serviceInstance);

    /**
     * 更新服务事件
     * @param serviceInstance
     */
    void onUpdate(ServiceInstance<T> serviceInstance);

    /**
     * 服务事件类型
     */
    enum ServiceEventType{
        ON_REGISTER,
        ON_UNREGISTER,
        ON_UPDATE
    }
}
