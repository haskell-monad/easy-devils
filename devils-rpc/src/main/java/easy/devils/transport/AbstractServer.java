package easy.devils.transport;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.nio.NioEventLoopGroup;

/**
 * @author limengyu
 * @create 2017/11/22
 */
public abstract class AbstractServer {

    public ServerBootstrap serverBootstrap;
    public NioEventLoopGroup bossGroup;
    public NioEventLoopGroup workGroup;

    public AbstractServer(){
        serverBootstrap = new ServerBootstrap();


    }

    public void start(){

    }
}
