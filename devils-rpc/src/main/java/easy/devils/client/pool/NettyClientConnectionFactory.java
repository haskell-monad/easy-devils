package easy.devils.client.pool;

import org.apache.commons.pool2.KeyedPooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import easy.devils.client.Connection;
import easy.devils.protocol.ServerInfo;
import easy.devils.transport.NettyClient;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

/**
 * @author limengyu
 * @create 2017/11/29
 */
public class NettyClientConnectionFactory implements KeyedPooledObjectFactory<ServerInfo<NettyClient>,Connection> {

    public static final Logger LOGGER = LoggerFactory.getLogger(NettyClientConnectionFactory.class);

    @Override
    public PooledObject<Connection> makeObject(ServerInfo<NettyClient> serverInfo) throws Exception {
        //创建connection对象
        Connection connection = new Connection();
        //获取连接
        NettyClient nettyClient = serverInfo.getClient();
        if(nettyClient == null){
            nettyClient = new NettyClient(serverInfo);
        }
        //连接服务器
        ChannelFuture channelFuture = nettyClient.connectServer();
        channelFuture.awaitUninterruptibly();
        if(!channelFuture.isSuccess()){
            LOGGER.error("NettyClient connect server fail: {}", channelFuture.cause());
        }else{
            connection.setConnection(true);
        }
        connection.setChannelFuture(channelFuture);
        return new DefaultPooledObject<>(connection);
    }

    @Override
    public void destroyObject(ServerInfo<NettyClient> key, PooledObject<Connection> p) throws Exception {
        p.getObject().close();
    }

    @Override
    public boolean validateObject(ServerInfo<NettyClient> key, PooledObject<Connection> p) {
        Connection conn = p.getObject();
        Channel channel = conn.getChannelFuture().channel();
        return conn.isConnection() && channel.isOpen() && channel.isActive() && conn.ping();
    }

    @Override
    public void activateObject(ServerInfo<NettyClient> key, PooledObject<Connection> p) throws Exception {

    }

    @Override
    public void passivateObject(ServerInfo<NettyClient> key, PooledObject<Connection> p) throws Exception {

    }
}
