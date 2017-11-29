package easy.devils.client.cluster.ha;

import easy.devils.client.cluster.loadbalance.AbstractLoadBalance;
import easy.devils.codec.DevilsMessage;
import easy.devils.codec.DevilsRequest;
import easy.devils.protocol.ServerInfo;
import easy.devils.transport.NettyClient;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;

/**
 * @author limengyu
 * @create 2017/11/28
 */
public class FailoverHaStrategyImpl extends AbstractHaStrategy {

    public FailoverHaStrategyImpl(GenericKeyedObjectPoolConfig config) {
        super(config);
    }

    @Override
    public Object call(DevilsMessage<DevilsRequest> message, AbstractLoadBalance loadBalance) {
        return null;
    }
}
