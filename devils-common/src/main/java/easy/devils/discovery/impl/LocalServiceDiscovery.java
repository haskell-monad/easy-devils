package easy.devils.discovery.impl;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import easy.devils.common.DevilsConstant;
import easy.devils.exception.DevilsFrameworkException;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.ServiceInstanceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import easy.devils.discovery.AbstractServiceDiscovery;
import easy.devils.discovery.IServiceEventListener;

/**
 * 本地Map服务注册中心
 * @author limengyu
 * @create 2017/11/18
 */
public class LocalServiceDiscovery<T> extends AbstractServiceDiscovery<T> {

    private static final Logger logger = LoggerFactory.getLogger(LocalServiceDiscovery.class);
    /**
     * Map<服务id，服务实例>
     */
    private Map<String,ServiceInstance<T>> REGISTER_CENTER = Maps.newConcurrentMap();

    private String address;

    public LocalServiceDiscovery() {
        this.address = DevilsConstant.LOCAL_DEFAULT_ADDRESS;
    }

    public void register(String serviceName){
        registerLocal(serviceName,address);
    }

    @Override
    public void start() {

    }

    public void registerLocal(String serviceName,String addressList){
        logger.debug("serviceName: {},addressList: {}",serviceName,addressList);
        Iterator<String> iterator = Splitter.on(",").split(addressList).iterator();
        while (iterator.hasNext()){
            String address = iterator.next();
            String[] hostPort = address.split(":");
            if(hostPort == null || hostPort.length != 2){
                continue;
            }
            ServiceInstanceBuilder<T> builder;
            try {
                builder = ServiceInstance.<T>builder();
            } catch (Exception e) {
                throw new DevilsFrameworkException();
            }
            ServiceInstance<T> serviceInstance = builder
                    .id(UUID.randomUUID().toString().replace("-", ""))
                    .name(serviceName)
                    .address(hostPort[0])
                    .port(Integer.parseInt(hostPort[1]))
                    .build();
            registerService(serviceInstance);
        }
    }

    @Override
    public void registerService(ServiceInstance<T> serviceInstance) {
        REGISTER_CENTER.put(serviceInstance.getId(),serviceInstance);
        notify(serviceInstance, IServiceEventListener.ServiceEventType.ON_REGISTER);
    }

    @Override
    public void unregisterService(ServiceInstance<T> serviceInstance) {
        REGISTER_CENTER.remove(serviceInstance.getId());
        notify(serviceInstance, IServiceEventListener.ServiceEventType.ON_UNREGISTER);
    }

    @Override
    public void updateService(ServiceInstance<T> serviceInstance) {
        REGISTER_CENTER.put(serviceInstance.getId(),serviceInstance);
        notify(serviceInstance, IServiceEventListener.ServiceEventType.ON_UPDATE);
    }

    @Override
    public Collection<String> queryServiceNameList() {
        if(REGISTER_CENTER.size() == 0){
            return Lists.newArrayList();
        }
        List<String> nameList = REGISTER_CENTER.values().stream().map(ServiceInstance::getName).collect(Collectors.toList());
        return nameList;
    }

    @Override
    public Collection<ServiceInstance<T>> queryServiceInstance(String serviceName) {
        List<ServiceInstance<T>> instanceList = REGISTER_CENTER.values().stream()
                .filter(instance -> instance.getName().equals(serviceName))
                .collect(Collectors.toList());
        return instanceList;
    }

    @Override
    public ServiceInstance<T> queryServiceInstance(String serviceName, String instanceId) throws Exception {
        return REGISTER_CENTER.get(instanceId);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
