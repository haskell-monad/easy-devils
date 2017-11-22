package easy.devils.config;

import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @author limengyu
 * @create 2017/11/22
 */
public class ServerConfig {

    /**
     * 扫描包路径
     */
    @Value("${scan.base.package:easy.devils}")
    public String basePackage;

    /**
     * tcp属性配置
     */
    @Value("${tcp.noDelay:true}")
    public boolean tcpNoDelay;

    @Value("${tcp.keepAlive:true}")
    public boolean soKeepAlive;

    @Value("${tcp.backlog:65535}")
    public int soBacklog;

    /**
     * 线程池配置
     */
    @Value("${biz.core.thread:20}")
    public int coreThread;

    @Value("${biz.max.thread:100}")
    public int maxThread;

    /**
     * http服务
     */
    @Value("${http.port:8090}")
    public int httpPort;

    @Value("${http.start:false}")
    public boolean startHttpServer;

    /**
     * zk连接配置
     */
    @Value("${zk.connection:localhost:2181}")
    public String zkConnStr;

    @Value("${zk.basePath:/easy-devils/service}")
    public String zkBasePath;

}
