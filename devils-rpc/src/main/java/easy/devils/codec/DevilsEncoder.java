package easy.devils.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 请求编码器
 * @author limengyu
 * @create 2017/11/23
 */
public class DevilsEncoder extends MessageToByteEncoder<DevilsMessage<DevilsRequest>>{
    @Override
    protected void encode(ChannelHandlerContext context, DevilsMessage<DevilsRequest> devilsRequestDevilsMessage, ByteBuf byteBuf) throws Exception {

    }
}
