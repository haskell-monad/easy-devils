package easy.devils.serialize;

/**
 * @author limengyu
 * @create 2017/11/08
 */
public interface ISerialize {

    /**
     * 序列化
     * @param t
     * @param <T>
     * @return
     */
    <T> byte[] serialize(T t);

    /**
     * 反序列化
     * @param bytes
     * @param t
     * @param <T>
     * @return
     */
    <T> T deserialize(byte[] bytes,T t);
}
