package easy.devils.transport;

import easy.devils.config.ServerConfig;
import easy.devils.server.IServiceRouting;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * @author limengyu
 * @create 2017/11/22
 */
public class HttpServer extends AbstractServer {

    public HttpServer(ServerConfig serverConfig) {
        super(serverConfig,null,serverConfig.httpPort);
    }


    @Override
    protected ChannelInitializer<SocketChannel> buildChannelInitializer(IServiceRouting serviceRouting) {
        //TODO
        return null;
    }
}
