package easy.devils.config;

import com.google.common.collect.Maps;
import easy.devils.common.DevilsConstant;
import easy.devils.protocol.HaStrategyType;
import easy.devils.protocol.LoadBalanceType;

import java.util.List;
import java.util.Map;

/**
 * @author limengyu
 * @create 2017/11/22
 */
public class ServiceProviderConfig {

    /**
     * 服务名称
     */
    private String serviceName;
    /**
     * ha策略
     */
    private HaStrategyType haStrategyType;
    /**
     * 负载均衡策略
     */
    private LoadBalanceType loadBalanceType;
    /**
     * 连接超时时间(毫秒)
     */
    private long connectionTimeOut;
    /**
     * 权重
     */
    private int weight;
    /**
     * 所属服务组
     */
    private String group;
    /**
     * 指定主机地址
     */
    private List<String> host;
    /**
     * 服务最大线程数量
     */
    private int maxThreadNum;
    /**
     * 服务方法信息
     */
    private Map<String,MethodProviderConfig> methodConfigMap = Maps.newConcurrentMap();

    public Map<String, MethodProviderConfig> getMethodConfigMap() {
        return methodConfigMap;
    }

    public int getMaxThreadNum() {
        return maxThreadNum;
    }

    public void setMaxThreadNum(int maxThreadNum) {
        this.maxThreadNum = maxThreadNum;
    }

    public void setMethodConfigMap(Map<String, MethodProviderConfig> methodConfigMap) {
        this.methodConfigMap = methodConfigMap;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public HaStrategyType getHaStrategyType() {
        return haStrategyType;
    }

    public void setHaStrategyType(HaStrategyType haStrategyType) {
        this.haStrategyType = haStrategyType;
    }

    public LoadBalanceType getLoadBalanceType() {
        return loadBalanceType;
    }

    public void setLoadBalanceType(LoadBalanceType loadBalanceType) {
        this.loadBalanceType = loadBalanceType;
    }

    public long getConnectionTimeOut() {
        return connectionTimeOut;
    }

    public void setConnectionTimeOut(long connectionTimeOut) {
        this.connectionTimeOut = connectionTimeOut;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public List<String> getHost() {
        return host;
    }

    public void setHost(List<String> host) {
        this.host = host;
    }
    public void addMethod(String methodName,MethodProviderConfig methodConfig){
        this.methodConfigMap.put(methodName,methodConfig);
    }

    public static class Builder{
        private ServiceProviderConfig serviceProviderConfig;
        public static Builder createBuilder(){
            Builder builder = new Builder();
            builder.serviceProviderConfig = new ServiceProviderConfig();
            return builder;
        }
        public Builder setServiceName(String serviceName){
            this.serviceProviderConfig.setServiceName(serviceName);
            return this;
        }
        public Builder setHaStrategyType(HaStrategyType haStrategyType){
            this.serviceProviderConfig.setHaStrategyType(haStrategyType);
            return this;
        }
        public Builder setLoadBalanceType(LoadBalanceType loadBalanceType){
            this.serviceProviderConfig.setLoadBalanceType(loadBalanceType);
            return this;
        }
        public Builder setConnectionTimeOut(long timeOut){
            this.serviceProviderConfig.setConnectionTimeOut(timeOut);
            return this;
        }
        public Builder setWeight(int weight){
            if(DevilsConstant.WEIGHT_MAX <= weight){
                weight = DevilsConstant.WEIGHT_MAX;
            }else if(DevilsConstant.WEIGHT_MIN > weight){
                weight = DevilsConstant.WEIGHT_MIN;
            }
            this.serviceProviderConfig.setWeight(weight);
            return this;
        }
        public Builder setGroup(String group){
            this.serviceProviderConfig.setGroup(group);
            return this;
        }
        public Builder setMethod(String methodName,MethodProviderConfig methodConfig){
            this.serviceProviderConfig.addMethod(methodName,methodConfig);
            return this;
        }
        public Builder setMaxThreadNum(int maxThreadNum){
            this.serviceProviderConfig.setMaxThreadNum(maxThreadNum);
            return this;
        }
        public ServiceProviderConfig build(){
            return this.serviceProviderConfig;
        }
    }

}
