package easy.devils.server;

import com.google.common.util.concurrent.RateLimiter;
import easy.devils.interceptor.InvokerInterceptor;
import easy.devils.interceptor.impl.InterceptorChain;

import java.lang.reflect.InvocationTargetException;
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
    /**
     * 服务限流器
     */
    private RateLimiter rateLimiter;
    /**
     * 拦截器链
     */
    private InterceptorChain interceptorChain;


    public MethodAction(Object serviceBean,Method method) {
        this.serviceBean = serviceBean;
        this.method = method;
    }

    /**
     * 执行目标方法
     * @param params
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public Object invokeMethod(Object... params) throws InvocationTargetException, IllegalAccessException {
        return method.invoke(serviceBean,params);
    }

    /**
     * 执行限流
     * @param params
     */
    public Object invokerRateLimiter(Object... params) throws InvocationTargetException, IllegalAccessException {
        if(rateLimiter != null){
            rateLimiter.acquire();
        }
        return invokerInterceptorChain(params);
    }

    /**
     * 执行拦截器
     * @param params
     * @return
     */
    public Object invokerInterceptorChain(Object... params) throws InvocationTargetException, IllegalAccessException {
        if(interceptorChain == null){
            return invokeMethod(params);
        }
        interceptorChain.beforeInvoker(serviceBean,method,params);
        Object result = interceptorChain.processInvoker(serviceBean, method, params);
        if(result == null){
            result = invokeMethod(params);
        }
        interceptorChain.afterInvoker(serviceBean,method,result);
        return result;
    }

    /**
     * 为方法添加拦截器
     * @param invokerInterceptor
     */
    public void addInterceptor(InvokerInterceptor invokerInterceptor){
        if(interceptorChain == null){
            interceptorChain = new InterceptorChain();
        }
        interceptorChain.addInterceptor(invokerInterceptor);
    }

    public RateLimiter getRateLimiter() {
        return rateLimiter;
    }

    public void setRateLimiter(RateLimiter rateLimiter) {
        this.rateLimiter = rateLimiter;
    }

    public InterceptorChain getInterceptorChain() {
        return interceptorChain;
    }

    public void setInterceptorChain(InterceptorChain interceptorChain) {
        this.interceptorChain = interceptorChain;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object getServiceBean() {
        return serviceBean;
    }

    public void setServiceBean(Object serviceBean) {
        this.serviceBean = serviceBean;
    }
}
