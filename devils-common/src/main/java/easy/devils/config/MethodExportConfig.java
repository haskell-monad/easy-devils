package easy.devils.config;

/**
 * 暴露方法
 * @author limengyu
 * @create 2017/11/22
 */
public class MethodExportConfig {

    private String methodName;

    public MethodExportConfig() {
    }

    public MethodExportConfig(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}
