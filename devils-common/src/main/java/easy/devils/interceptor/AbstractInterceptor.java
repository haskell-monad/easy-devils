package easy.devils.interceptor;

import java.lang.reflect.Method;

/**
 * @author limengyu
 * @create 2017/12/19
 */
public abstract class AbstractInterceptor implements InvokerInterceptor {

    @Override
    public boolean beforeInvoker(Object target, Method method, Object... params) {
        return true;
    }

    @Override
    public boolean afterInvoker(Object target, Method method, Object... params) {
        return true;
    }

    @Override
    public Object processInvoker(Object target, Method method, Object params) {
        return null;
    }
}
