package easy.devils.client.cluster.loadbalance;

import easy.devils.codec.DevilsMessage;
import easy.devils.codec.DevilsRequest;
import easy.devils.protocol.ServerInfo;
import easy.devils.transport.NettyClient;

/**
 * 随机加权
 * @author limengyu
 * @create 2017/11/28
 */
public class RandomWeightLoadBalanceImpl extends AbstractLoadBalance {
    @Override
    public ServerInfo<ServerInfo<NettyClient>> selectNode(DevilsMessage<DevilsRequest> devilsMessage) {
        return null;
    }
}
