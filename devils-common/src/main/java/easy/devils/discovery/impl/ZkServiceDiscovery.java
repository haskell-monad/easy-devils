package easy.devils.discovery.impl;

import easy.devils.common.DevilsConstant;
import easy.devils.discovery.AbstractServiceDiscovery;
import easy.devils.discovery.IServiceEventListener;
import easy.devils.protocol.MetaInfo;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.x.discovery.ServiceDiscovery;
import org.apache.curator.x.discovery.ServiceDiscoveryBuilder;
import org.apache.curator.x.discovery.ServiceInstance;
import org.apache.curator.x.discovery.details.InstanceSerializer;
import org.apache.curator.x.discovery.details.JsonInstanceSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * zk服务注册中心
 * @author limengyu
 * @create 2017/11/18
 */
public class ZkServiceDiscovery extends AbstractServiceDiscovery<MetaInfo> implements TreeCacheListener{

    private static final Logger logger = LoggerFactory.getLogger(ZkServiceDiscovery.class);

    /**
     * zk地址
     */
    private String address = "localhost:2181";
    /**
     * zk元数据序列化
     */
    private static final InstanceSerializer instanceSerializer = new JsonInstanceSerializer<>(MetaInfo.class);
    private static final int sleepTimeMs = 2000;
    private static final int maxRetries = 5;
    private ServiceDiscovery<MetaInfo> serviceDiscovery;

    @Override
    public void start() throws Exception {
        //连接到zk
        CuratorFramework zkClient = CuratorFrameworkFactory.newClient(address,new ExponentialBackoffRetry(sleepTimeMs, maxRetries));
        zkClient.start();
        serviceDiscovery = ServiceDiscoveryBuilder.builder(MetaInfo.class)
                .client(zkClient)
                .basePath(DevilsConstant.ZK_BASE_PATH)
                .serializer(instanceSerializer)
                .build();
        serviceDiscovery.start();
        logger.debug("connect zk server[{}] success...",address);
    }

    @Override
    public void registerService(ServiceInstance<MetaInfo> serviceInstance) throws Exception {
        serviceDiscovery.registerService(serviceInstance);
    }

    @Override
    public void unregisterService(ServiceInstance<MetaInfo> serviceInstance) throws Exception {
        serviceDiscovery.unregisterService(serviceInstance);
    }

    @Override
    public void updateService(ServiceInstance<MetaInfo> serviceInstance) throws Exception {
        serviceDiscovery.updateService(serviceInstance);
    }

    @Override
    public Collection<String> queryServiceNameList() throws Exception {
        return serviceDiscovery.queryForNames();
    }

    @Override
    public Collection<ServiceInstance<MetaInfo>> queryServiceInstance(String serviceName) throws Exception {
        return serviceDiscovery.queryForInstances(serviceName);
    }

    @Override
    public ServiceInstance<MetaInfo> queryServiceInstance(String serviceName, String instanceId) throws Exception {
        return serviceDiscovery.queryForInstance(serviceName,instanceId);
    }

    @Override
    public void childEvent(CuratorFramework zkClient, TreeCacheEvent treeCacheEvent) throws Exception {
        ServiceInstance serviceInstance = instanceSerializer.deserialize(treeCacheEvent.getData().getData());
        switch (treeCacheEvent.getType()){
            case NODE_ADDED:
                notify(serviceInstance,IServiceEventListener.ServiceEventType.ON_REGISTER);
                break;
            case NODE_UPDATED:
                notify(serviceInstance,IServiceEventListener.ServiceEventType.ON_UPDATE);
                break;
            case NODE_REMOVED:
                notify(serviceInstance,IServiceEventListener.ServiceEventType.ON_UNREGISTER);
                break;
        }
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
