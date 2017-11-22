package easy.devils.protocol;

import org.apache.curator.x.discovery.ServiceInstance;

/**
 * 服务器信息
 * @author limengyu
 * @create 2017/11/22
 */
public class ServerInfo<T> {

    private String host;
    private int port;
    private T client;

    public ServerInfo() {
    }

    public ServerInfo(ServiceInstance serviceInstance) {
        this.host = serviceInstance.getAddress();
        this.port = serviceInstance.getPort();
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public T getClient() {
        return client;
    }

    public void setClient(T client) {
        this.client = client;
    }


}
