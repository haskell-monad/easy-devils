package easy.devils.transport;

import easy.devils.config.ServerConfig;
import easy.devils.config.ServiceExportConfig;
import easy.devils.server.IServiceRouting;

/**
 * 创建DevilsServer
 * @author limengyu
 * @create 2017/11/25
 */
public class DevilsServerFactory {

    private ServerConfig serverConfig;

    public DevilsServerFactory(ServerConfig serverConfig){
        this.serverConfig = serverConfig;
    }

    public DevilsServer createDevilsServer(IServiceRouting serviceRouting,int port){
        DevilsServer devilsServer = new DevilsServer(serverConfig,serviceRouting,port);
        return devilsServer;
    }
}
