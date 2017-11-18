package easy.devils.codec.serialize.impl;

import easy.devils.codec.serialize.ISerialize;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.ProtostuffOutput;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

import java.io.IOException;

/**
 * Google ProtoBuf 序列化
 * @author limengyu
 * @create 2017/11/8
 */
public class ProtoStuffSerializeImpl implements ISerialize {

    @Override
    public <T> byte[] serialize(T obj) throws IOException {
        Schema<T> schema = (Schema<T>)RuntimeSchema.getSchema(obj.getClass());
        LinkedBuffer buffer = LinkedBuffer.allocate(1024 * 1024);
        byte[] bytes;
        try {
            bytes = ProtostuffIOUtil.toByteArray(obj,schema,buffer);
        }catch (Exception e){
            throw new RuntimeException("序列化异常",e);
        }finally {
            buffer.clear();
        }
        return bytes;
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) throws IOException, ClassNotFoundException {
        T t;
        try {
           t = clazz.newInstance();
        } catch (Exception e) {
           throw new RuntimeException("反序列化异常",e);
        }
        Schema<T> schema = RuntimeSchema.getSchema(clazz);
        ProtostuffIOUtil.mergeFrom(bytes,t,schema);
        return t;
    }
}
