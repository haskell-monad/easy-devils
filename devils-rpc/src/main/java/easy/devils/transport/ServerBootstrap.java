package easy.devils.transport;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.curator.x.discovery.ServiceInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.google.common.collect.Sets;

import easy.devils.annotation.ServiceExport;
import easy.devils.config.ServerConfig;
import easy.devils.config.ServiceExportConfig;
import easy.devils.discovery.IServiceDiscovery;
import easy.devils.discovery.impl.LocalServiceDiscovery;
import easy.devils.exception.DevilsFrameworkException;
import easy.devils.processor.AbstractAnnotationProcessorProvider;
import easy.devils.processor.IAnnotationProcessor;
import easy.devils.protocol.MetaInfo;
import easy.devils.server.DevilsServiceRouting;
import easy.devils.utils.NetUtils;

/**
 * @author limengyu
 * @create 2017/11/25
 */
public class ServerBootstrap {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerBootstrap.class);
    private static final AbstractAnnotationProcessorProvider DEFAULT_PROVIDER = AbstractAnnotationProcessorProvider.DEFAULT_PROVIDER;
    private DevilsServerFactory devilsServerFactory;

    private boolean startHttpServer;
    private ServerConfig serverConfig;
    private ApplicationContext applicationContext;
    private IServiceDiscovery serviceDiscovery;

    /**
     * 唯一标识(service|port)
     */
    private Set<String> UNIQUE_SERVICE_KEY = Sets.newHashSet();

    /**
     * 初始化并启动服务器
     * @throws InterruptedException
     */
    public void start() throws InterruptedException {
        if(applicationContext == null){
            throw new DevilsFrameworkException();
        }
        this.serverConfig = applicationContext.getBean(ServerConfig.class);
        this.devilsServerFactory = new DevilsServerFactory(serverConfig);
        if(this.serviceDiscovery == null){
           this.serviceDiscovery = new LocalServiceDiscovery<>();
        }
        this.startDevilsServer();
        this.startHttpServer();
    }

    /**
     * 初始化并启动DevilsServer
     * @throws InterruptedException
     */
    private void startDevilsServer(){
        List<IAnnotationProcessor> processorList = DEFAULT_PROVIDER.getProcessorList();
        //获取所有有@ServiceExport注解的类
        String[] serviceExports = applicationContext.getBeanNamesForAnnotation(ServiceExport.class);
        LOGGER.info("exist [{}] service can export.", serviceExports.length);
        for (String serviceBeanName : serviceExports) {
            Object serviceBean = applicationContext.getBean(serviceBeanName);
            Class<?> serviceBeanClass = serviceBean.getClass();
            Method[] serviceMethods = serviceBeanClass.getMethods();
            if(serviceMethods == null || serviceMethods.length == 0){
                continue;
            }
            //构造ServiceExportConfig
            ServiceExportConfig serviceExportConfig = new ServiceExportConfig();
            for (Method serviceMethod : serviceMethods) {
                for (IAnnotationProcessor processor : processorList) {
                    if(processor.accept(serviceBeanClass,serviceMethod)){
                        processor.process(serviceBeanClass,serviceMethod,serviceExportConfig);
                    }
                }
            }
            //初始化路由
            DevilsServiceRouting serviceRouting = new DevilsServiceRouting(applicationContext);
            serviceRouting.init(serviceBean, serviceExportConfig);
            //注册服务(服务名|端口号)
            String providerServiceName = serviceExportConfig.getServiceName();
            int exportPort = serviceExportConfig.getPort();
            String uniqueKey = providerServiceName + "|" + exportPort;
            if(StringUtils.isBlank(uniqueKey)
                || exportPort == 0 || UNIQUE_SERVICE_KEY.contains(uniqueKey)) {
                continue;
            }
            UNIQUE_SERVICE_KEY.add(uniqueKey);
            try {
                //启动DevilsServer
                DevilsServer devilsServer = devilsServerFactory.createDevilsServer(serviceRouting, serviceExportConfig.getPort());
                devilsServer.startServer();
                ServiceInstance<MetaInfo> serviceInstance = ServiceInstance
                        .<MetaInfo>builder()
                        .name(providerServiceName)
                        .port(exportPort)
                        .address(NetUtils.getLocalAddress().getHostAddress())
                        .build();
                serviceDiscovery.registerService(serviceInstance);
            }catch (Exception e){
                LOGGER.error("start DevilsServer error",e);
                throw new DevilsFrameworkException(e);
            }

        }
    }

    /**
     * 启动HttpServer
     * @throws InterruptedException
     */
    private void startHttpServer() throws InterruptedException {
        if(startHttpServer){
            new HttpServer(this.serverConfig).startServer();
        }
    }

    public boolean isStartHttpServer() {
        return startHttpServer;
    }

    public void setStartHttpServer(boolean startHttpServer) {
        this.startHttpServer = startHttpServer;
    }

    public ServerConfig getServerConfig() {
        return serverConfig;
    }

    public void setServerConfig(ServerConfig serverConfig) {
        this.serverConfig = serverConfig;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public IServiceDiscovery getServiceDiscovery() {
        return serviceDiscovery;
    }

    public void setServiceDiscovery(IServiceDiscovery serviceDiscovery) {
        this.serviceDiscovery = serviceDiscovery;
    }
}
