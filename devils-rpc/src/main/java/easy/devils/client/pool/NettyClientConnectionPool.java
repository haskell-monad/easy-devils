package easy.devils.client.pool;

import easy.devils.client.Connection;
import easy.devils.protocol.ServerInfo;
import easy.devils.transport.NettyClient;
import org.apache.commons.pool2.KeyedPooledObjectFactory;
import org.apache.commons.pool2.impl.GenericKeyedObjectPool;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;

/**
 * Connection对象池
 * @author limengyu
 * @create 2017/11/29
 */
public class NettyClientConnectionPool extends GenericKeyedObjectPool<ServerInfo<NettyClient>,Connection> {

    public NettyClientConnectionPool(KeyedPooledObjectFactory<ServerInfo<NettyClient>, Connection> factory) {
        super(factory);
    }

    public NettyClientConnectionPool(KeyedPooledObjectFactory<ServerInfo<NettyClient>, Connection> factory, GenericKeyedObjectPoolConfig config) {
        super(factory, config);
    }
}
