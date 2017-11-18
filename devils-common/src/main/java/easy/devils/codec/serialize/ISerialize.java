package easy.devils.codec.serialize;

import java.io.IOException;

/**
 * @author limengyu
 * @create 2017/11/08
 */
public interface ISerialize {

    /**
     * 序列化
     * @param obj
     * @return
     * @throws IOException
     */
    <T> byte[] serialize(T obj) throws IOException;

    /**
     * 反序列化
     * @param bytes
     * @param clazz
     * @param <T>
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    <T> T deserialize(byte[] bytes,Class<T> clazz) throws IOException, ClassNotFoundException;
}
