package easy.devils.client.cluster.loadbalance;

import easy.devils.client.cluster.FailoverCheckManager;
import easy.devils.codec.DevilsMessage;
import easy.devils.codec.DevilsRequest;
import easy.devils.discovery.AbstractServiceDiscovery;
import easy.devils.protocol.ServerInfo;
import easy.devils.transport.NettyClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 随机
 * @author limengyu
 * @create 2017/11/28
 */
public class RandomLoadBalanceImpl<T> extends AbstractLoadBalance<T>{


    public RandomLoadBalanceImpl(AbstractServiceDiscovery serviceDiscovery, String serviceName, FailoverCheckManager failoverCheckManager) {
        super(serviceDiscovery, serviceName, failoverCheckManager);
    }

    @Override
    public ServerInfo<T> selectNode(DevilsMessage<DevilsRequest> devilsMessage) {
        List<ServerInfo<T>> list = getAvailableServerInfoList();
        int idx = (int) (ThreadLocalRandom.current().nextDouble() * list.size());
        return list.get(idx % list.size());
    }
}
