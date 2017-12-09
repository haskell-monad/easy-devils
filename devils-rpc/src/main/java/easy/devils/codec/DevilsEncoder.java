package easy.devils.codec;

import easy.devils.codec.compress.ICompress;
import easy.devils.codec.serialize.ISerialize;
import easy.devils.protocol.CompressType;
import easy.devils.protocol.EventType;
import easy.devils.protocol.SerializeType;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 请求编码器
 * @author limengyu
 * @create 2017/11/23
 */
public class DevilsEncoder extends MessageToByteEncoder<DevilsMessage>{
    @Override
    protected void encode(ChannelHandlerContext context, DevilsMessage devilsMessage, ByteBuf byteBuf) throws Exception {

        DevilsHeader devilsHeader = devilsMessage.getDevilsHeader();

        byteBuf.writeShort(devilsHeader.getMagic());
        byteBuf.writeByte(devilsHeader.getVersion());
        byteBuf.writeByte(devilsHeader.getExtend());
        byteBuf.writeLong(devilsHeader.getMessageId());

        Object content = devilsMessage.getContent();
        if(content == null && EventType.isHeartbeat(devilsHeader.getExtend())){
            //心跳消息,消息体为空
            byteBuf.writeInt(0);
        }else{
            ISerialize serialize = SerializeType.getSerializeTypeByExtend(devilsHeader.getExtend());
            ICompress compress = CompressType.getCompressTypeByExtend(devilsHeader.getExtend());
            byte[] payload = compress.compress(serialize.serialize(devilsMessage.getContent()));
            byteBuf.writeInt(payload.length);
            byteBuf.writeBytes(payload);
        }
    }
}
