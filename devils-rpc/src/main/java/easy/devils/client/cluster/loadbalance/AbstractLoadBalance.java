package easy.devils.client.cluster.loadbalance;


import easy.devils.client.cluster.ILoadBalance;
import easy.devils.protocol.ServerInfo;
import easy.devils.transport.NettyClient;

/**
 * @author limengyu
 * @create 2017/11/28
 */
public abstract class AbstractLoadBalance implements ILoadBalance<ServerInfo<NettyClient>> {


    public void fail(){

    }

}
