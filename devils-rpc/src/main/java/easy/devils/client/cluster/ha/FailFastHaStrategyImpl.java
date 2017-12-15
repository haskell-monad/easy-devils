package easy.devils.client.cluster.ha;

import easy.devils.client.cluster.loadbalance.AbstractLoadBalance;
import easy.devils.codec.DevilsMessage;
import easy.devils.codec.DevilsRequest;
import easy.devils.protocol.ServerInfo;
import easy.devils.transport.NettyClient;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;
import org.apache.curator.x.discovery.ServiceInstance;

/**
 * @author limengyu
 * @create 2017/11/28
 */
public class FailFastHaStrategyImpl extends AbstractHaStrategy{

    public FailFastHaStrategyImpl(GenericKeyedObjectPoolConfig config) {
        super(config);
    }


    @Override
    public Object call(DevilsMessage<DevilsRequest> message,AbstractLoadBalance<ServerInfo<NettyClient>> loadBalance) {
        ServerInfo serverInfo = loadBalance.selectNode(message);
        return remoteCall(serverInfo, message, loadBalance);
    }

    @Override
    public void onRegister(ServiceInstance serviceInstance) {

    }

    @Override
    public void onUpdate(ServiceInstance serviceInstance) {

    }
}
