package easy.devils.processor;

import java.lang.reflect.Method;

import easy.devils.annotation.MethodProvider;
import easy.devils.annotation.ServiceProvider;
import easy.devils.config.MethodProviderConfig;
import easy.devils.config.ServiceProviderConfig;
import org.apache.commons.lang3.StringUtils;

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
    public void process(Class<?> clazz, Method method, Object obj) {
        if(obj instanceof ServiceProviderConfig){
            ServiceProviderConfig serviceConfig = (ServiceProviderConfig)obj;
            //读取接口方法上的配置信息
            MethodProvider methodProvider = method.getDeclaredAnnotation(MethodProvider.class);
            String methodName = StringUtils.isBlank(methodProvider.methodName()) ? method.getName() : methodProvider.methodName();
            MethodProviderConfig methodConfig = MethodProviderConfig.Builder.createBuilder()
                    .setServiceName(serviceConfig.getServiceName())
                    .setMethodName(methodName)
                    .setCompress(methodProvider.compress())
                    .setSerialize(methodProvider.serialize())
                    .build();
            serviceConfig.addMethod(methodName,methodConfig);
        }
    }

}
