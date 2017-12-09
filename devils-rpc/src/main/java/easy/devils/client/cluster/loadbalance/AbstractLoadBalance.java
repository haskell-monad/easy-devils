package easy.devils.client.cluster.loadbalance;


import easy.devils.client.cluster.FailoverCheckManager;
import easy.devils.client.cluster.ILoadBalance;
import easy.devils.client.cluster.ServerInfoManager;
import easy.devils.discovery.AbstractServiceDiscovery;
import easy.devils.protocol.ServerInfo;
import easy.devils.transport.NettyClient;

/**
 * @author limengyu
 * @create 2017/11/28
 */
public abstract class AbstractLoadBalance<T> extends ServerInfoManager<T> implements ILoadBalance<T>{

    public AbstractLoadBalance(AbstractServiceDiscovery serviceDiscovery, String serviceName, FailoverCheckManager failoverCheckManager) {
        super(serviceDiscovery, serviceName, failoverCheckManager);
    }

}
