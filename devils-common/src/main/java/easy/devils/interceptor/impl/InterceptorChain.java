package easy.devils.interceptor.impl;

import com.google.common.collect.Lists;
import easy.devils.interceptor.InvokerInterceptor;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

/**
 * 拦截器链
 * @author limengyu
 * @create 2017/12/9
 */
public class InterceptorChain implements InvokerInterceptor {

    private List<InvokerInterceptor> interceptors = Lists.newArrayList();

    @Override
    public boolean beforeInvoker(Object target, Method method, Object... params) {
        boolean noInterrupt = true;
        Iterator<InvokerInterceptor> iterator = interceptors.iterator();
        while (noInterrupt && iterator.hasNext()){
            noInterrupt = iterator.next().beforeInvoker(target, method, params);
        }
        return noInterrupt;
    }

    @Override
    public boolean afterInvoker(Object target, Method method, Object... params) {
        boolean noInterrupt = true;
        Iterator<InvokerInterceptor> iterator = interceptors.iterator();
        while (noInterrupt && iterator.hasNext()){
            noInterrupt = iterator.next().afterInvoker(target, method, params);
        }
        return noInterrupt;
    }

    @Override
    public Object processInvoker(Object target, Method method, Object params) {
        Object result = null;
        for (InvokerInterceptor interceptor : interceptors) {
            Object obj = interceptor.processInvoker(target, method, params);
            if(obj != null){
                result = obj;
            }
        }
        return result;
    }

    /**
     * 添加拦截器
     * @param invokerInterceptor
     */
    public void addInterceptor(InvokerInterceptor invokerInterceptor){
        interceptors.add(invokerInterceptor);
    }
}
