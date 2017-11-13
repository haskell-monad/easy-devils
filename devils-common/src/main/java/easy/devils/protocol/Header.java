package easy.devils.protocol;

/**
 * @author limengyu
 * @create 2017/11/9
 */
public class Header {

    /**
     * 16-byte
     * 魔数 2-byte
     * 版本 1-byte
     * 序列化（0-2）、压缩（3-4）、事件（5-6）、消息类型（7） 1-byte
     * 消息Id      8-byte
     * payload长度 4-byte
     */
    public static final short MAGIC = (short)0xF0F0;

    public static final short VERSION = 0;

    enum version{
        v1((byte)1,16),
        v2((byte)2,16);
        version(byte version,int headerLength){

        }
    }
}
