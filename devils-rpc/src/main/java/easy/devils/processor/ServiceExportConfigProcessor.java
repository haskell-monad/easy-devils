package easy.devils.processor;

import java.lang.reflect.Method;

import easy.devils.annotation.MethodExport;
import easy.devils.annotation.MethodProvider;
import easy.devils.annotation.ServiceExport;
import easy.devils.annotation.ServiceProvider;
import easy.devils.config.MethodExportConfig;
import easy.devils.config.ServiceExportConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.MethodUtils;

/**
 * @author limengyu
 * @create 2017/11/25
 */
public class ServiceExportConfigProcessor implements IAnnotationProcessor{

    @Override
    public boolean accept(Class<?> clazz, Method method) {
        if(clazz.isAnnotationPresent(ServiceExport.class)
            && method.isAnnotationPresent(MethodExport.class)){
            return true;
        }
        return false;
    }

    @Override
    public void process(Class<?> clazz, Method method,Object obj) {
        if(obj instanceof ServiceExportConfig){
            ServiceExportConfig config = (ServiceExportConfig)obj;
            int exportPort = clazz.getAnnotation(ServiceExport.class).port();
            //获取该类所有有@ServiceProvider注解的父接口
            //然后从中找到method方法的接口注解声明
            //找到暴露的服务名、服务端口、方法名
            Class<?>[] interfaces = clazz.getInterfaces();
            if(interfaces == null || interfaces.length == 0){
                return;
            }
            for (Class<?> interfaceClass : interfaces) {
                if(!interfaceClass.isAnnotationPresent(ServiceProvider.class)){
                    continue;
                }
                Method[] methods = interfaceClass.getMethods();
                if(methods == null || methods.length == 0){
                    continue;
                }
                ServiceProvider serviceProvider = interfaceClass.getAnnotation(ServiceProvider.class);
                String providerServiceName = StringUtils.isBlank(serviceProvider.serviceName()) ? interfaceClass.getSimpleName() : serviceProvider.serviceName();
                config.setServiceName(providerServiceName);
                config.setPort(exportPort);
                Method interfaceMethod = MethodUtils.getMatchingAccessibleMethod(interfaceClass, method.getName(), method.getParameterTypes());
                if(interfaceMethod.isAnnotationPresent(MethodProvider.class)){
                    String exportMethodName = interfaceMethod.getAnnotation(MethodProvider.class).methodName();
                    if(StringUtils.isBlank(exportMethodName)){
                        exportMethodName = interfaceMethod.getName();
                    }
                    config.addMethod(method,new MethodExportConfig(exportMethodName));
                }
            }
        }
    }

}
