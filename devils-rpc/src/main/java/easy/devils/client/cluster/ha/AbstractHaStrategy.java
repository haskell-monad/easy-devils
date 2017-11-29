package easy.devils.client.cluster.ha;

import easy.devils.client.NettyResponseFuture;
import easy.devils.codec.DevilsResponse;
import easy.devils.common.DevilsConstant;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;

import easy.devils.client.Connection;
import easy.devils.client.cluster.IHaStrategy;
import easy.devils.client.cluster.loadbalance.AbstractLoadBalance;
import easy.devils.client.pool.NettyClientConnectionFactory;
import easy.devils.client.pool.NettyClientConnectionPool;
import easy.devils.codec.DevilsMessage;
import easy.devils.codec.DevilsRequest;
import easy.devils.exception.DevilsFrameworkException;
import easy.devils.protocol.ServerInfo;
import easy.devils.transport.NettyClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author limengyu
 * @create 2017/11/28
 */
public abstract class AbstractHaStrategy implements IHaStrategy{

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractHaStrategy.class);

    /**
     * 连接对象池
     */
    private GenericKeyedObjectPool<ServerInfo<NettyClient>,Connection> pool;

    public AbstractHaStrategy(GenericKeyedObjectPoolConfig config) {
        this.pool = new NettyClientConnectionPool(new NettyClientConnectionFactory(),config);
    }

    /**
     * 发起远程调用
     */
    protected Object remoteCall(ServerInfo<NettyClient> serverInfo,DevilsMessage<DevilsRequest> message,AbstractLoadBalance<ServerInfo<NettyClient>> loadBalance){
        Object result = null;
        if(serverInfo == null){
            throw new DevilsFrameworkException("can't get available service");
        }
        Connection connection = null;
        try {
            connection = pool.borrowObject(serverInfo);
            //获取连接对象,然后发起调用
            NettyResponseFuture<DevilsResponse> response = connection.sendRequest(message, DevilsConstant.CLIENT_TIME_OUT);
            result = response.getPromise().await(DevilsConstant.CLIENT_TIME_OUT, TimeUnit.MILLISECONDS).getResult();
            //返还连接对象
            pool.returnObject(serverInfo,connection);
        } catch (Exception e) {
            LOGGER.error("remoteCall error", e);
            //上报调用失败的服务器节点
            loadBalance.fail(serverInfo);
            //销毁该连接
            if(connection != null){
                try {
                    pool.invalidateObject(serverInfo,connection);
                } catch (Exception error) {
                    LOGGER.error(error.getMessage(),e);
                }
            }
            return null;
        }finally {
            serverInfo.incrementAndGet();
        }
        return result;
    }

}
