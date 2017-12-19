package easy.devils.utils;

import easy.devils.protocol.CompressType;
import easy.devils.protocol.SerializeType;

/**
 * @author limengyu
 * @create 2017/11/25
 */
public class DevilsUtils {

    /**
     * 构建路由地址
     * @return
     */
    public static String builderRoutingUri(String serviceName,String methodName){

        return "/"+serviceName+"/"+methodName;
    }

    /**
     * 7 | (1 << 4) = 7 | 16 = 23
     * (23 & 24) == (1 << 4)
     * (23 & 7) == 7
     * @param serializeType
     * @param compressType
     * @return
     */
    public static byte getExtend(SerializeType serializeType,CompressType compressType){
        return (byte)(serializeType.getValue() | compressType.getValue());
    }
}
