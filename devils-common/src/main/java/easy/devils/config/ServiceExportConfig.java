package easy.devils.config;

import com.google.common.collect.Maps;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * 暴露的服务-方法信息
 * @author limengyu
 * @create 2017/11/22
 */
public class ServiceExportConfig {

    /**
     * 暴露的服务名
     */
    private String serviceName;
    /**
     * 暴露服务所在端口
     */
    private int port;
    /**
     * 该服务下的方法信息
     */
    private Map<Method,MethodExportConfig> methodExportConfigMap = Maps.newConcurrentMap();

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Map<Method, MethodExportConfig> getMethodExportConfigMap() {
        return methodExportConfigMap;
    }

    public void setMethodExportConfigMap(Map<Method, MethodExportConfig> methodExportConfigMap) {
        this.methodExportConfigMap = methodExportConfigMap;
    }

    public void addMethod(Method method,MethodExportConfig methodExportConfig){
        this.methodExportConfigMap.put(method,methodExportConfig);
    }

    /**
     * 获取方法的名称
     * @param method
     * @return
     */
    public String getExportMethodName(Method method){
        return methodExportConfigMap.get(method) == null ? null : methodExportConfigMap.get(method).getMethodName();
    }
}
