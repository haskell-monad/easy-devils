package easy.devils.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 请求解码器
 * @author limengyu
 * @create 2017/11/23
 */
public class DevilsDecoder extends ByteToMessageDecoder{

    @Override
    protected void decode(ChannelHandlerContext context, ByteBuf byteBuf, List<Object> list) throws Exception {

    }
}
