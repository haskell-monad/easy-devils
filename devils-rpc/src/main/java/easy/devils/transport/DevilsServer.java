package easy.devils.transport;

import easy.devils.codec.DevilsDecoder;
import easy.devils.codec.DevilsEncoder;
import easy.devils.config.ServerConfig;
import easy.devils.config.ServiceExportConfig;
import easy.devils.handler.DevilsServerHandler;
import easy.devils.server.IServiceRouting;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * Devils服务器
 * @author limengyu
 * @create 2017/11/22
 */
public class DevilsServer extends AbstractServer {

	public DevilsServer(ServerConfig serverConfig,IServiceRouting serviceRouting,int port) {
		super(serverConfig,serviceRouting,port);
	}

	@Override
	protected ChannelInitializer<SocketChannel> buildChannelInitializer(IServiceRouting serviceRouting) {
		return new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel sc) throws Exception {
				sc.pipeline().addLast("decoder", new DevilsDecoder());
				sc.pipeline().addLast("encoder", new DevilsEncoder());
				sc.pipeline().addLast("processor", new DevilsServerHandler(serviceRouting));
			}
		};
	}
}
