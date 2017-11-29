package easy.devils.client;

import easy.devils.codec.DevilsMessage;
import easy.devils.codec.DevilsRequest;
import easy.devils.codec.DevilsResponse;
import io.netty.channel.Channel;
import io.netty.util.concurrent.Future;

/**
 * 请求响应回调对象
 * @author limengyu
 * @create 2017/11/29
 */
public class NettyResponseFuture<T> {

    private Channel channel;
    private DevilsMessage<DevilsRequest> message;
    private Promise<T> promise;
    private long requestTime;
    private long timeout;

    public NettyResponseFuture(Channel channel, DevilsMessage<DevilsRequest> message, Promise promise, long requestTime, long timeout) {
        this.promise = promise;
        this.channel = channel;
        this.message = message;
        this.requestTime = requestTime;
        this.timeout = timeout;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public DevilsMessage<DevilsRequest> getMessage() {
        return message;
    }

    public void setMessage(DevilsMessage<DevilsRequest> message) {
        this.message = message;
    }

    public Promise<T> getPromise() {
        return promise;
    }

    public void setPromise(Promise<T> promise) {
        this.promise = promise;
    }

    public long getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(long requestTime) {
        this.requestTime = requestTime;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }
}
