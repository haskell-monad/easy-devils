package easy.devils.server;

/**
 * 服务路由
 * @author limengyu
 * @create 2017/11/25
 */
public interface IServiceRouting {

    /**
     * 根据地址查询具体的服务方法
     * @param url
     */
    MethodAction lookupMethodAction(String uri);
}
