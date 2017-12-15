package easy.devils.client.cluster;

import easy.devils.client.cluster.loadbalance.AbstractLoadBalance;
import easy.devils.codec.DevilsMessage;
import easy.devils.codec.DevilsRequest;
import easy.devils.protocol.ServerInfo;
import easy.devils.transport.NettyClient;

/**
 * @author limengyu
 * @create 2017/11/28
 */
public interface IHaStrategy<T> {

    /**
     * 通过负载均衡器获取到一个可用的服务节点，然后发起调用
     * @param message 请求消息
     * @param loadBalance 负载均衡器
     * @return 调用结果
     */
    Object call(DevilsMessage<DevilsRequest> message,AbstractLoadBalance<T> loadBalance);
}
