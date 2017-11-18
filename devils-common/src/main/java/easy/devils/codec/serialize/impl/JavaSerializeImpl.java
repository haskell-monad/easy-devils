package easy.devils.codec.serialize.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import easy.devils.codec.serialize.ISerialize;

/**
 * GSon 序列化
 * @author limengyu
 * @create 2017/11/8
 */
public class JavaSerializeImpl implements ISerialize {
    @Override
    public <T> byte[] serialize(T obj) throws IOException {
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream(1024);
        ObjectOutputStream outputStream = new ObjectOutputStream(arrayOutputStream);
        outputStream.writeObject(obj);
        outputStream.flush();
        return arrayOutputStream.toByteArray();
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) throws IOException, ClassNotFoundException {
        ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream inputStream = new ObjectInputStream(arrayInputStream);
        return (T)inputStream.readObject();
    }

}
