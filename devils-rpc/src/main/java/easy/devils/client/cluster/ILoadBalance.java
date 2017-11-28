package easy.devils.client.cluster;

import easy.devils.codec.DevilsMessage;
import easy.devils.codec.DevilsRequest;
import easy.devils.protocol.ServerInfo;

/**
 * @author limengyu
 * @create 2017/11/28
 */
public interface ILoadBalance<T> {

    /**
     * 根据负载均衡策略获取一个可用的节点
     * @param devilsMessage
     * @return
     */
    ServerInfo<T> selectNode(DevilsMessage<DevilsRequest> devilsMessage);
}
