package easy.devils.discovery;


import com.google.common.collect.Maps;
import org.apache.curator.x.discovery.ServiceInstance;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author limengyu
 * @create 2017/11/18
 */
public abstract class AbstractServiceDiscovery<T> implements IServiceDiscovery {

    /**
     * 服务名称,List<服务事件>
     */
    private Map<String,List<AbstractServiceEventListener<T>>> SERVICE_EVENT_MAP = Maps.newConcurrentMap();


    public abstract void start();

    /**
     * 通知相应事件
     * @param serviceInstance
     * @param eventType
     */
    protected void notify(ServiceInstance<T> serviceInstance,IServiceEventListener.ServiceEventType eventType){
        String serviceName = serviceInstance.getName();
        List<AbstractServiceEventListener<T>> list = SERVICE_EVENT_MAP.get(serviceName);
        if(list == null || list.size() == 0){
            return;
        }
        for (AbstractServiceEventListener<T> eventListener : list) {
            eventListener.onRefresh(serviceInstance,eventType);
        }
    }
}
