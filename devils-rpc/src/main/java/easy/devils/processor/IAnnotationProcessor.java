package easy.devils.processor;

import java.lang.reflect.Method;

/**
 * 处理注解
 * @author limengyu
 * @create 2017/11/25
 */
public interface IAnnotationProcessor {

    /**
     * 判断是否可以处理
     * @param clazz
     * @param method
     * @return
     */
    boolean accept(Class<?> clazz,Method method);

    /**
     * 进行处理
     * @param clazz
     * @param method
     * @param obj
     * @return
     */
    void process(Class<?> clazz, Method method,Object obj);

}
