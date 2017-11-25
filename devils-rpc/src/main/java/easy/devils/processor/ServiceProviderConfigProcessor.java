package easy.devils.processor;

import java.lang.reflect.Method;

import easy.devils.annotation.MethodProvider;
import easy.devils.annotation.ServiceProvider;

/**
 * @author limengyu
 * @create 2017/11/25
 */
public class ServiceProviderConfigProcessor implements IAnnotationProcessor{

    @Override
    public boolean accept(Class<?> clazz, Method method) {
        if(clazz.isAnnotationPresent(ServiceProvider.class)
            && method.isAnnotationPresent(MethodProvider.class)){
            return true;
        }
        return false;
    }

    @Override
    public void process() {

    }
}
