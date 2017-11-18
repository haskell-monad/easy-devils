package easy.devils.codec;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author limengyu
 * @create 2017/11/18
 */
public class DevilsRequest implements Serializable{
    private static final long serialVersionUID = 1708845267185009688L;

    private String serviceName;
    private String methodName;
    private Object[] params;

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

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "DevilsRequest{" +
                "serviceName='" + serviceName + '\'' +
                ", methodName='" + methodName + '\'' +
                ", params=" + Arrays.toString(params) +
                '}';
    }
}
