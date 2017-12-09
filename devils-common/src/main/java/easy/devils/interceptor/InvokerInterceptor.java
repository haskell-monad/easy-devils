package easy.devils.interceptor;

import java.lang.reflect.Method;

/**
 * 方法拦截器
 * @author limengyu
 * @create 2017/12/9
 */
public interface InvokerInterceptor {

    /**
     * 方法执行前拦截
     * @param target
     * @param method
     * @param params
     * @return
     */
    boolean beforeInvoker(Object target,Method method,Object... params);

    /**
     * 方法执行后拦截
     * @param target
     * @param method
     * @param params
     * @return
     */
    boolean afterInvoker(Object target,Method method,Object... params);

    /**
     * 处理调用
     * @param target
     * @param method
     * @param params
     * @return
     */
    Object processInvoker(Object target,Method method,Object params);
}
