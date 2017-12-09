package easy.devils.codec;

import easy.devils.codec.compress.ICompress;
import easy.devils.codec.serialize.ISerialize;
import easy.devils.common.DevilsConstant;
import easy.devils.exception.DevilsFrameworkException;
import easy.devils.protocol.CompressType;
import easy.devils.protocol.EventType;
import easy.devils.protocol.MessageType;
import easy.devils.protocol.SerializeType;
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

        if(byteBuf.readableBytes() < DevilsConstant.HEADER_SIZE){
            return;
        }
        byteBuf.markReaderIndex();
        short magic = byteBuf.readShort();
        //验证magic
        if(DevilsConstant.MAGIC != magic){
            byteBuf.resetReaderIndex();
            throw new DevilsFrameworkException(String.format("DevilsDecoder magic header[%s] not support",magic));
        }
        byte version = byteBuf.readByte();
        byte extend = byteBuf.readByte();
        long messageId = byteBuf.readLong();
        int payLoadSize = byteBuf.readInt();

        Object request = null;
        if(!(payLoadSize == 0 && EventType.isHeartbeat(extend))){
            //非心跳消息
            if(payLoadSize > byteBuf.readableBytes()){
                byteBuf.resetReaderIndex();
                return;
            }
            byte[] payload = new byte[payLoadSize];
            byteBuf.readBytes(payload);
            ICompress compress = CompressType.getCompressTypeByExtend(extend);
            ISerialize serialize = SerializeType.getSerializeTypeByExtend(extend);
            request = serialize.deserialize(compress.uncompress(payload), MessageType.getMessageTypeByExtend(extend));
        }
        DevilsHeader devilsHeader = new DevilsHeader(magic,version,extend,messageId,payLoadSize);
        DevilsMessage devilsMessage = new DevilsMessage(devilsHeader,request);
        list.add(devilsMessage);
    }
}
