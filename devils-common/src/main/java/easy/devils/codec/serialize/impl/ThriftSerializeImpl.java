package easy.devils.codec.serialize.impl;

import easy.devils.codec.serialize.ISerialize;

import java.io.IOException;

/**
 * Thrift 序列化
 * @author limengyu
 * @create 2017/11/8
 */
public class ThriftSerializeImpl implements ISerialize {
    @Override
    public <T> byte[] serialize(T obj) throws IOException {
        return new byte[0];
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) throws IOException, ClassNotFoundException {
        return null;
    }
}
