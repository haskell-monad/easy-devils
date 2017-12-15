package easy.devils.handler;

import easy.devils.client.Connection;
import easy.devils.client.NettyResponseFuture;
import easy.devils.codec.DevilsMessage;
import easy.devils.codec.DevilsResponse;
import easy.devils.common.DevilsConstant;
import easy.devils.protocol.EventType;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 处理服务器响应
 * @author limengyu
 * @create 2017/12/9
 */
public class DevilsClientHandler extends SimpleChannelInboundHandler<DevilsMessage<DevilsResponse>>{

    private static final Logger LOGGER = LoggerFactory.getLogger(DevilsClientHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext,DevilsMessage<DevilsResponse> devilsMessage) throws Exception {
        NettyResponseFuture<DevilsResponse> future = Connection.CALLBACK_QUEUE.remove(devilsMessage.getDevilsHeader().getMessageId());
        if(future == null){
            LOGGER.warn("response future is null,It has been reclaimed by TimeoutMonitor Thread,messageId: {}", devilsMessage.getDevilsHeader().getMessageId());
            return;
        }
        DevilsResponse response = devilsMessage.getContent();
        if(response == null && EventType.isHeartbeat(devilsMessage.getDevilsHeader().getExtend())){
            response = new DevilsResponse();
            response.setCode(DevilsConstant.HEARTBEAT_CODE);
        }
        future.getPromise().onReceive(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        ctx.channel().close().addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
               LOGGER.info("close channel remoteAddress: {}", ctx.channel().remoteAddress());
            }
        });
    }
}
