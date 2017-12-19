package easy.devils.server;

import com.google.common.collect.Maps;
import com.google.common.util.concurrent.RateLimiter;
import easy.devils.annotation.Interceptor;
import easy.devils.annotation.MethodExport;
import easy.devils.annotation.Rate;
import easy.devils.common.DevilsConstant;
import easy.devils.config.MethodExportConfig;
import easy.devils.interceptor.InvokerInterceptor;
import easy.devils.server.IServiceRouting;
import easy.devils.config.ServiceExportConfig;
import easy.devils.utils.DevilsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Objects;

/**
 * 服务路由
 * @author limengyu
 * @create 2017/11/25
 */
public class DevilsServiceRouting implements IServiceRouting {

    private static final Logger LOGGER = LoggerFactory.getLogger(DevilsServiceRouting.class);

    private ApplicationContext context;

    /**
     * 路由-方法映射
     */
    private Map<String,MethodAction> routMapping = Maps.newConcurrentMap();

    public DevilsServiceRouting(ApplicationContext context){
        this.context = context;
    }

    /**
     * @param serviceBean 服务实现类
     * @param serviceExportConfig 暴露的服务信息
     */
    public void init(Object serviceBean,ServiceExportConfig serviceExportConfig){
        Method[] methods = serviceBean.getClass().getMethods();
        for (Method method : methods) {
            if(!method.isAnnotationPresent(MethodExport.class)){
                continue;
            }
            String serviceName = serviceExportConfig.getServiceName();
            String exportMethodName = serviceExportConfig.getExportMethodName(method);
            String routUri = DevilsUtils.builderRoutingUri(serviceName, exportMethodName);
            LOGGER.info("init-routing: {}",routUri);
            MethodAction methodAction = new MethodAction(serviceBean,method);
            this.addMethodRoutMapping(routUri, methodAction);
            this.initInterceptor(methodAction);
            this.initRate(methodAction);
        }
    }

    /**
     * 初始化拦截器
     * @param methodAction
     */
    private void initInterceptor(MethodAction methodAction) {
        if(!methodAction.getMethod().isAnnotationPresent(Interceptor.class)){
            return;
        }
        if(context == null){
            LOGGER.warn("applicationContext is null,skip initInterceptor");
            return;
        }
        Interceptor interceptor = methodAction.getMethod().getDeclaredAnnotation(Interceptor.class);
        Class<?> clazz = interceptor.value();
        String[] beanIds = interceptor.beanId();
        if(Object.class != clazz){
            Object bean = context.getBean(clazz);
            this.addInterceptor(methodAction, bean);
        }else if(beanIds != null && beanIds.length > 0){
            for (String beanId : beanIds) {
                this.addInterceptor(methodAction, context.getBean(beanId));
            }
        }
    }

    private void addInterceptor(MethodAction methodAction,Object bean){
        if(bean != null && bean instanceof InvokerInterceptor){
            methodAction.addInterceptor((InvokerInterceptor)bean);
        }
    }

    /**
     * 初始化限流
     * @param methodAction
     */
    private void initRate(MethodAction methodAction) {
        if(!methodAction.getMethod().isAnnotationPresent(Rate.class)){
            return;
        }
        Rate rate = methodAction.getMethod().getAnnotation(Rate.class);
        double value = rate.value();
        if(value < 0){
            value = DevilsConstant.DEFAULT_RATE;
            LOGGER.warn("method [{}] @Rate < 0,will use default rate: [{}]",methodAction.getMethod().getName(),DevilsConstant.DEFAULT_RATE);
        }
        methodAction.setRateLimiter(RateLimiter.create(value));
    }

    /**
     * 保存路由-方法映射
     * @param uri
     * @param methodAction
     */
    private void addMethodRoutMapping(String uri,MethodAction methodAction){
        routMapping.put(uri,methodAction);
    }

    /**
     * 根据路由查询具体执行方法
     * @param uri
     * @return
     */
    @Override
    public MethodAction lookupMethodAction(String uri) {
        return routMapping.get(uri);
    }
}
