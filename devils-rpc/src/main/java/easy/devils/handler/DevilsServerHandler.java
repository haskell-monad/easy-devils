package easy.devils.handler;


import easy.devils.codec.DevilsMessage;
import easy.devils.codec.DevilsRequest;
import easy.devils.server.IServiceRouting;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 处理客户端请求handler
 * @author limengyu
 * @create 2017/11/23
 */
public class DevilsServerHandler extends SimpleChannelInboundHandler<DevilsMessage<DevilsRequest>>{

    private IServiceRouting serviceRouting;

    public DevilsServerHandler(IServiceRouting serviceRouting){
        this.serviceRouting = serviceRouting;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DevilsMessage<DevilsRequest> devilsRequestDevilsMessage) throws Exception {
        //TODO
    }
}
