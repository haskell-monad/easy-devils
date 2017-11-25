package easy.devils.transport;

import java.util.concurrent.atomic.AtomicReference;

import easy.devils.config.ServerConfig;
import easy.devils.server.IServiceRouting;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author limengyu
 * @create 2017/11/22
 */
public abstract class AbstractServer {

    public static final Logger logger = LoggerFactory.getLogger(AbstractServer.class);
    public ServerBootstrap serverBootstrap;
    public NioEventLoopGroup bossGroup;
    public NioEventLoopGroup workGroup;
    public Channel channel;
    public ServerConfig serverConfig;
    public int port;
    public AtomicReference<ServerState> serverState;

    public AbstractServer(ServerConfig serverConfig,IServiceRouting serviceRouting,int port){
        this.port = port;
        this.serverBootstrap = new ServerBootstrap();
        this.bossGroup = new NioEventLoopGroup();
        this.workGroup = new NioEventLoopGroup();
        this.serverConfig = serverConfig;
        this.serverBootstrap.group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, this.serverConfig.tcpNoDelay)
                .option(ChannelOption.SO_KEEPALIVE, this.serverConfig.soKeepAlive)
                .option(ChannelOption.SO_BACKLOG, this.serverConfig.soBacklog)
                .handler(new LoggingHandler(LogLevel.DEBUG))
                .childHandler(buildChannelInitializer(serviceRouting));
        this.serverState = new AtomicReference<>(ServerState.Created);
    }

    /**
     * ChannelHandler
     * @param serviceRouting
     * @return
     */
    protected abstract ChannelInitializer<SocketChannel> buildChannelInitializer(IServiceRouting serviceRouting);

    /**
     * 启动服务
     */
    public ChannelFuture startServer() throws InterruptedException {
        if(!this.serverState.compareAndSet(ServerState.Created,ServerState.Starting)){
            throw new IllegalStateException("Server already start...");
        }
        ChannelFuture channelFuture = serverBootstrap.bind(this.port);
        logger.info("Server start success,bind port [],soBacklog: [],soKeepLive: [],tcpNodDelay: []",
                this.port,this.serverConfig.soBacklog,this.serverConfig.soKeepAlive,this.serverConfig.tcpNoDelay);
        this.channel = channelFuture.channel();
        this.channel.closeFuture();
        this.serverState.set(ServerState.Started);
        return channelFuture;
    }

    /**
     * 停止服务器
     */
    public void shutDownServer() throws InterruptedException {
        if(!serverState.compareAndSet(ServerState.Started,ServerState.Shutdown)){
            throw new IllegalStateException("Server already shutdown...");
        }
        channel.close().sync();
    }

    public void stop(){
        if(bossGroup != null){
            bossGroup.shutdownGracefully().awaitUninterruptibly();
        }
        if(workGroup != null){
            workGroup.shutdownGracefully().awaitUninterruptibly();
        }
    }

    /**
     * 服务状态
     */
    enum ServerState{
        /**
         * 已创建
         */
        Created,
        /**
         * 启动中
         */
        Starting,
        /**
         * 已启动
         */
        Started,
        /**
         * 已停止
         */
        Shutdown
    }

}
