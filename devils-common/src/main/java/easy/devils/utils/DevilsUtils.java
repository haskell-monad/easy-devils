package easy.devils.utils;

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
}
