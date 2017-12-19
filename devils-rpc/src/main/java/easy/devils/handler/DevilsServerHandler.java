package easy.devils.handler;


import easy.devils.DevilsContext;
import easy.devils.codec.DevilsHeader;
import easy.devils.codec.DevilsMessage;
import easy.devils.codec.DevilsRequest;
import easy.devils.codec.DevilsResponse;
import easy.devils.exception.DevilsErrorMessage;
import easy.devils.exception.DevilsExceptionConstant;
import easy.devils.exception.DevilsFrameworkException;
import easy.devils.protocol.EventType;
import easy.devils.protocol.MessageType;
import easy.devils.server.IServiceRouting;
import easy.devils.server.MethodAction;
import easy.devils.utils.DevilsUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

/**
 * 处理客户端请求handler
 * @author limengyu
 * @create 2017/11/23
 */
public class DevilsServerHandler extends SimpleChannelInboundHandler<DevilsMessage<DevilsRequest>>{

    private static final Logger LOGGER = LoggerFactory.getLogger(DevilsServerHandler.class);
    /**
     * 业务线程池
     */
    private final static ExecutorService executor = new StandardThreadExecutor();
    /**
     * 服务路由
     */
    private IServiceRouting serviceRouting;

    public DevilsServerHandler(IServiceRouting serviceRouting){
        this.serviceRouting = serviceRouting;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext,DevilsMessage<DevilsRequest> devilsMessage) throws Exception {
        DevilsHeader devilsHeader = devilsMessage.getDevilsHeader();
        if(EventType.isHeartbeat(devilsHeader.getExtend())){
            //心跳消息
            channelHandlerContext.writeAndFlush(devilsMessage);
            return;
        }
        //通过路由查询相应的服务
        DevilsRequest request = devilsMessage.getContent();
        String uri = DevilsUtils.builderRoutingUri(request.getServiceName(), request.getMethodName());
        MethodAction methodAction = serviceRouting.lookupMethodAction(uri);
        if(methodAction == null){
            LOGGER.warn("not found service mapping[{}]",uri);
            return;
        }
        executor.submit(new InvokerServiceRunnable(devilsMessage,methodAction,channelHandlerContext));
    }

}

/**
 * 调用远程服务
 */
class InvokerServiceRunnable implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(InvokerServiceRunnable.class);

    private DevilsMessage<DevilsRequest> devilsMessage;
    private MethodAction methodAction;
    private ChannelHandlerContext channelHandlerContext;

    public InvokerServiceRunnable(DevilsMessage<DevilsRequest> devilsMessage, MethodAction methodAction, ChannelHandlerContext channelHandlerContext) {
        this.devilsMessage = devilsMessage;
        this.methodAction = methodAction;
        this.channelHandlerContext = channelHandlerContext;
    }

    @Override
    public void run() {
        DevilsResponse response = new DevilsResponse();
        Object result = null;
        DevilsContext.setDevilsContext(channelHandlerContext.channel(), devilsMessage);
        try {
            result = methodAction.invokerRateLimiter(devilsMessage.getContent().getParams());
        }catch (Exception e){
            LOGGER.error(String.format("invoker service fail: %s",e.getMessage()),e);
            response.setDevilsErrorMessage(DevilsExceptionConstant.FRAMEWORK_DEFAULT_ERROR);
        }finally {
            DevilsContext.removeDevilsContext();
        }
        //返回响应
        byte extend = (byte)(devilsMessage.getDevilsHeader().getExtend() | MessageType.RESPONSE_TYPE_MESSAGE);
        devilsMessage.getDevilsHeader().setExtend(extend);
        response.setResult(result);
        DevilsMessage<DevilsResponse> message = new DevilsMessage<>(devilsMessage.getDevilsHeader(), response);
        channelHandlerContext.writeAndFlush(message);
    }
}
