package easy.devils.client.cluster;

import easy.devils.codec.DevilsMessage;
import easy.devils.codec.DevilsRequest;
import easy.devils.protocol.ServerInfo;
import easy.devils.transport.NettyClient;

/**
 * @author limengyu
 * @create 2017/11/28
 */
public interface IHaStrategy {

    /**
     * 调用远程服务
     * @param message 请求消息
     * @param serverInfo 服务器节点信息
     * @return 调用结果
     */
    Object call(DevilsMessage<DevilsRequest> message,ServerInfo<NettyClient> serverInfo);
}
