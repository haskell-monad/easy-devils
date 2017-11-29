package easy.devils.client;

import com.google.common.collect.Maps;
import easy.devils.codec.DevilsMessage;
import easy.devils.codec.DevilsRequest;
import easy.devils.codec.DevilsResponse;
import easy.devils.exception.DevilsFrameworkException;
import io.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 客户端服务器连接对象
 * @author limengyu
 * @create 2017/11/29
 */
public class Connection implements Closeable{

    private static final Logger LOGGER = LoggerFactory.getLogger(Connection.class);
    /**
     * 保存请求信息
     * <消息id,netty响应>
     */
    private static final Map<Long,NettyResponseFuture<DevilsResponse>> CALLBACK_QUEUE = Maps.newConcurrentMap();
    /**
     * 消息id
     */
    private static final AtomicLong MESSAGE_ID = new AtomicLong(0);
    /**
     * 当前是否已连接到服务端
     */
    private AtomicBoolean isConn = new AtomicBoolean(false);
    private ChannelFuture channelFuture;

    /**
     * 发送请求
     * @param message 消息
     * @param timeout 超时时间
     * @return
     */
    public NettyResponseFuture<DevilsResponse> sendRequest(DevilsMessage<DevilsRequest> message,long timeout){
        if(!this.isConnection()){
            throw new DevilsFrameworkException("netty client is not connected server");
        }
        message.getDevilsHeader().setMessageId(MESSAGE_ID.incrementAndGet());
        //构造请求响应回调对象
        NettyResponseFuture<DevilsResponse> response = new NettyResponseFuture(channelFuture.channel(),message,new Promise(),System.currentTimeMillis(),timeout);
        this.addCallbackMap(message,response);
        try {
            this.channelFuture.channel().writeAndFlush(message);
        }catch (Exception e){
            LOGGER.error("sendRequest Error: {}",e);
            this.removeCallbackMap(message.getDevilsHeader().getMessageId());
        }
        return response;
    }

    public void addCallbackMap(DevilsMessage<DevilsRequest> message,NettyResponseFuture<DevilsResponse> responseMessage){
        CALLBACK_QUEUE.put(message.getDevilsHeader().getMessageId(), responseMessage);
    }

    public void removeCallbackMap(Long messageId){
        CALLBACK_QUEUE.remove(messageId);
    }

    /**
     * 设置是否连接
     * @param conn
     */
    public void setConnection(boolean conn) {
        isConn.set(conn);
    }

    /**
     * 心跳消息
     * @return
     */
    public boolean ping() {
        return false;
    }

    /**
     * 关闭channel
     */
    @Override
    public void close() {
        if(channelFuture != null){
            channelFuture.channel().close();
        }
    }

    public boolean isConnection(){
        return isConn.get();
    }
    public ChannelFuture getChannelFuture() {
        return channelFuture;
    }

    public void setChannelFuture(ChannelFuture channelFuture) {
        this.channelFuture = channelFuture;
    }
}
