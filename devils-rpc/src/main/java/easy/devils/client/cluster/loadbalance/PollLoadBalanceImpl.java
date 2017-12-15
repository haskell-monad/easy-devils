package easy.devils.client.cluster.loadbalance;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import easy.devils.client.cluster.FailoverCheckManager;
import easy.devils.codec.DevilsMessage;
import easy.devils.codec.DevilsRequest;
import easy.devils.discovery.AbstractServiceDiscovery;
import easy.devils.protocol.ServerInfo;

/**
 * 轮询
 * @author limengyu
 * @create 2017/11/28
 */
public class PollLoadBalanceImpl<T> extends AbstractLoadBalance<T>{


    public PollLoadBalanceImpl(AbstractServiceDiscovery serviceDiscovery, String serviceName, FailoverCheckManager failoverCheckManager) {
        super(serviceDiscovery, serviceName, failoverCheckManager);
    }


    @Override
    public ServerInfo<T> selectNode(DevilsMessage<DevilsRequest> devilsMessage) {
        return null;
    }
}
