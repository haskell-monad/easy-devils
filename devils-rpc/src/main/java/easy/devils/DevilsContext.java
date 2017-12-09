package easy.devils;

import com.google.common.collect.Maps;
import easy.devils.codec.DevilsMessage;
import easy.devils.exception.DevilsFrameworkException;
import io.netty.channel.Channel;

import java.util.Map;

/**
 * @author limengyu
 * @create 2017/12/9
 */
public class DevilsContext {

    private static final ThreadLocal<DevilsContext> THREAD_LOCAL = new ThreadLocal<>();

    private Channel channel;
    private DevilsMessage devilsMessage;
    private Map<String,String> attributes = Maps.newHashMap();

    public DevilsContext(Channel channel,DevilsMessage devilsMessage){
       this.channel = channel;
       this.devilsMessage = devilsMessage;
    }

    public static Channel getChannel(){
        return getDevilsContext().channel;
    }

    public static DevilsMessage getDevilsMessage(){
        return getDevilsContext().devilsMessage;
    }

    public static Map<String,String> getAttributes(){
        return getDevilsContext().attributes;
    }

    public static String getAttributeValue(String key){
        return getDevilsContext().attributes.get(key);
    }

    public static void setDevilsContext(Channel channel,DevilsMessage devilsMessage){
        THREAD_LOCAL.set(new DevilsContext(channel,devilsMessage));
    }

    public static void removeDevilsContext(){
        THREAD_LOCAL.remove();
    }

    public static DevilsContext getDevilsContext(){
        DevilsContext devilsContext = THREAD_LOCAL.get();
        if(devilsContext == null){
            throw new DevilsFrameworkException("thread local context is null");
        }
        return devilsContext;
    }
}
