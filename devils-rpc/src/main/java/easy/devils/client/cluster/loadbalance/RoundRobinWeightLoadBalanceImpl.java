package easy.devils.client.cluster.loadbalance;

import easy.devils.client.cluster.FailoverCheckManager;
import easy.devils.codec.DevilsMessage;
import easy.devils.codec.DevilsRequest;
import easy.devils.discovery.AbstractServiceDiscovery;
import easy.devils.protocol.ServerInfo;
import easy.devils.transport.NettyClient;

/**
 * 加权
 * @author limengyu
 * @create 2017/11/28
 */
public class RoundRobinWeightLoadBalanceImpl<T> extends AbstractLoadBalance<T> {

    public RoundRobinWeightLoadBalanceImpl(AbstractServiceDiscovery serviceDiscovery, String serviceName, FailoverCheckManager failoverCheckManager) {
        super(serviceDiscovery, serviceName, failoverCheckManager);
    }

    @Override
    public ServerInfo<T> selectNode(DevilsMessage<DevilsRequest> devilsMessage) {
        return null;
    }
}
