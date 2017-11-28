package easy.devils.client.cluster.ha;

import easy.devils.codec.DevilsMessage;
import easy.devils.codec.DevilsRequest;
import easy.devils.protocol.ServerInfo;
import easy.devils.transport.NettyClient;

/**
 * @author limengyu
 * @create 2017/11/28
 */
public class FailFastHaStrategyImpl extends AbstractHaStrategy{

    @Override
    public Object call(DevilsMessage<DevilsRequest> message, ServerInfo<NettyClient> serverInfo) {
        return null;
    }
}
