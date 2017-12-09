package easy.devils.protocol;

import org.apache.curator.x.discovery.ServiceInstance;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 服务器信息
 * @author limengyu
 * @create 2017/11/22
 */
public class ServerInfo<T> {

    private String host;
    private int port;
    private T client;

    private AtomicInteger count = new AtomicInteger(0);

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

    public void incrementAndGet(){
        count.incrementAndGet();
    }

    @Override
    public String toString() {
        return "ServerInfo{" +
                "host='" + host + '\'' +
                ", port=" + port +
                ", client=" + client +
                '}';
    }
}
