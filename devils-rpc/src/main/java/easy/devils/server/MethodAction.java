package easy.devils.server;

import java.lang.reflect.Method;

/**
 * @author limengyu
 * @create 2017/11/25
 */
public class MethodAction {
    /**
     * 服务bean
     */
    private Object serviceBean;
    /**
     * 服务方法
     */
    private Method method;


    public MethodAction(Object serviceBean,Method method) {
        this.serviceBean = serviceBean;
        this.method = method;
    }




}
