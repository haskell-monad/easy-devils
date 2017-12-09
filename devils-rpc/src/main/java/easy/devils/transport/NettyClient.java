package easy.devils.transport;

import java.io.Closeable;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import easy.devils.client.Connection;
import easy.devils.client.NettyResponseFuture;
import easy.devils.codec.DevilsDecoder;
import easy.devils.codec.DevilsEncoder;
import easy.devils.codec.DevilsResponse;
import easy.devils.common.DevilsConstant;
import easy.devils.handler.DevilsClientHandler;
import easy.devils.protocol.ServerInfo;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 根据serverInfo创建相应的NettyClient对象
 * @author limengyu
 * @create 2017/11/22
 */
public class NettyClient implements Closeable {
	private static final Logger LOGGER = LoggerFactory.getLogger(NettyClient.class);
	private Bootstrap bootstrap;
	private EventLoopGroup loopGroup;
	private String host;
	private int port;

	public NettyClient(ServerInfo<NettyClient> serverInfo) {
		this.host = serverInfo.getHost();
		this.port = serverInfo.getPort();
		init();
	}
	private void init() {
		this.bootstrap = new Bootstrap();
		this.loopGroup = new NioEventLoopGroup(4);
		this.bootstrap.group(loopGroup)
				// 关闭Nagle
				.option(ChannelOption.TCP_NODELAY, true)
				.option(ChannelOption.SO_KEEPALIVE, true)
				.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, DevilsConstant.CONNECTION_TIME_OUT)
				.channel(NioSocketChannel.class)
				.handler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel sc) throws Exception {
						sc.pipeline().addLast("encode", new DevilsEncoder());
						sc.pipeline().addLast("decode", new DevilsDecoder());
						sc.pipeline().addLast("handler", new DevilsClientHandler());
					}
				});
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat(String.format("timeout_monitor_[%s:%d]", host, port)).build();
        new ScheduledThreadPoolExecutor(1,threadFactory)
                .scheduleWithFixedDelay(new TimeoutConnectionMonitor(), 1000, 100, TimeUnit.MILLISECONDS);
	}
	/**
	 * 连接服务器
	 * 连接超时时间设置：option(ChannelOption.CONNECT_TIMEOUT_MILLIS)
	 * @return
	 */
	public ChannelFuture connectServer() {
		ChannelFuture connect = this.bootstrap.connect(host, port);
		connect.awaitUninterruptibly();
		return connect;
	}
	@Override
	public void close() throws IOException {
		this.loopGroup.shutdownGracefully();
	}

	/**
	 * 定时清空超时的NettyResponseFuture
	 * @author limengyu
	 * @create 2017/12/9
	 */
	class TimeoutConnectionMonitor implements Runnable{
		@Override
		public void run() {
			long currentTime = System.currentTimeMillis();
			Set<Map.Entry<Long, NettyResponseFuture<DevilsResponse>>> entries = Connection.CALLBACK_QUEUE.entrySet();
			for (Map.Entry<Long, NettyResponseFuture<DevilsResponse>> entry : entries) {
				try {
					NettyResponseFuture<DevilsResponse> future = entry.getValue();
					if(future.getRequestTime() + future.getTimeout() < currentTime){
						Connection.CALLBACK_QUEUE.remove(entry.getKey());
					}
				}catch (Exception e){
					LOGGER.error("clear timeout future error,messageId: "+entry.getKey(),e);
				}
			}
		}
	}
}

