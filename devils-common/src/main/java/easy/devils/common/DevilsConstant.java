package easy.devils.common;

/**
 * @author limengyu
 * @create 2017/11/9
 */
public class DevilsConstant {

    public static final String DEFAULT_CHARSET = "UTF-8";
    /**
     * 权重最大值
     */
    public static final int WEIGHT_MAX = 100;

    /**
     * 权重最小值
     */
    public static final int WEIGHT_MIN = 1;

    /**
     * zk基础路径
     *
     */
    public static final String ZK_BASE_PATH = "/easy-devils/service";
    /**
     * 服务端连接超时
     */
    public static final long CONNECTION_TIME_OUT = 2000;
    /**
     * 客户端连接超时
     */
    public static final long CLIENT_TIME_OUT = 2000;
    /**
     * 16-byte
     * 魔数 2-byte
     * 版本 1-byte
     * 序列化（0-2）、压缩（3-4）、事件（5-6）、消息类型（7） 1-byte
     * 消息Id      8-byte
     * payload长度 4-byte
     */
    public static final short MAGIC = (short)0xF0F0;




}
