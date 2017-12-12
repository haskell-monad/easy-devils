package easy.devils.config;

import easy.devils.common.DevilsConstant;
import easy.devils.protocol.CompressType;
import easy.devils.protocol.SerializeType;

/**
 * 方法配置信息
 * @author limengyu
 * @create 2017/11/22
 */
public class MethodProviderConfig {
    private String serviceName;
    private String methodName;
    private long timeout;
    private SerializeType serializeType;
    private CompressType compressType;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public SerializeType getSerializeType() {
        return serializeType;
    }

    public void setSerializeType(SerializeType serializeType) {
        this.serializeType = serializeType;
    }

    public CompressType getCompressType() {
        return compressType;
    }

    public void setCompressType(CompressType compressType) {
        this.compressType = compressType;
    }

    public static class Builder{
        private MethodProviderConfig methodConfig;
        public static Builder createBuilder(){
            Builder builder = new Builder();
            builder.methodConfig = new MethodProviderConfig();
            return builder;
        }
        public Builder loadDefault(){
            this.methodConfig.setCompressType(CompressType.DEFAULT_COMPRESS);
            this.methodConfig.setSerializeType(SerializeType.DEFAULT_SERIALIZE);
            this.methodConfig.setTimeout(DevilsConstant.CLIENT_TIME_OUT);
            return this;
        }
        public Builder setSerialize(SerializeType serializeType){
            this.methodConfig.setSerializeType(serializeType);
            return this;
        }
        public Builder setCompress(CompressType compressType){
            this.methodConfig.setCompressType(compressType);
            return this;
        }
        public Builder setTimeOut(long timeOut){
            this.methodConfig.setTimeout(timeOut);
            return this;
        }
        public Builder setMethodName(String methodName){
            this.methodConfig.setMethodName(methodName);
            return this;
        }
        public Builder setServiceName(String serviceName){
            this.methodConfig.setServiceName(serviceName);
            return this;
        }
        public MethodProviderConfig build(){
            return this.methodConfig;
        }
    }
}
