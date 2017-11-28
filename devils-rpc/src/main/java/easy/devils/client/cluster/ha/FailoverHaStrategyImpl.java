package easy.devils.client.cluster.ha;

import easy.devils.codec.DevilsMessage;
import easy.devils.codec.DevilsRequest;
import easy.devils.protocol.ServerInfo;
import easy.devils.transport.NettyClient;

/**
 * @author limengyu
 * @create 2017/11/28
 */
public class FailoverHaStrategyImpl extends AbstractHaStrategy {

    @Override
    public Object call(DevilsMessage<DevilsRequest> message, ServerInfo<NettyClient> serverInfo) {
        //获取所有可使用的节点
        //然后根据负载算法获取一个可使用的节点发起调用



        return null;
    }
}
