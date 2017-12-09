package easy.devils.client.cluster;

import easy.devils.protocol.ServerInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * 记录失败的节点信息
 * @author limengyu
 * @create 2017/11/29
 */
public class FailoverCheckManager<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(FailoverCheckManager.class);

    private Set<T> failList = new HashSet<>();


    public void fail(T t){
        failList.add(t);
        LOGGER.debug("serverInfo fail: {}",t);
    }

    public Set<T> failList(){
        return failList;
    }
}
