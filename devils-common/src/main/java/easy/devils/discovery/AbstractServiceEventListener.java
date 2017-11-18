package easy.devils.discovery;

import org.apache.curator.x.discovery.ServiceInstance;

/**
 * 服务事件监听器
 * @author limengyu
 * @create 2017/11/18
 */
public abstract class AbstractServiceEventListener<T> implements IServiceEventListener<T> {

    /**
     * 服务发生变化时触发相应事件
     * @param serviceInstance
     * @param serviceEventType
     */
    public void onRefresh(ServiceInstance<T> serviceInstance,ServiceEventType serviceEventType){
        switch (serviceEventType){
            case ON_REGISTER:
                onRegister(serviceInstance);
                break;
            case ON_UNREGISTER:
                onUnRegister(serviceInstance);
                break;
            case ON_UPDATE:
                onUpdate(serviceInstance);
                break;
        }
    }
}
