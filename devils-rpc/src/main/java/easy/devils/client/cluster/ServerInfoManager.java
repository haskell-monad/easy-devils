package easy.devils.client.cluster;

import easy.devils.discovery.AbstractServiceEventListener;
import easy.devils.protocol.ServerInfo;
import easy.devils.transport.NettyClient;
import org.apache.curator.x.discovery.ServiceInstance;

import java.util.List;

/**
 * @author limengyu
 * @create 2017/11/28
 */
public class ServerInfoManager extends AbstractServiceEventListener<ServerInfo<NettyClient>> {

//    /**
//     * 获取可使用服务列表
//     * @return
//     */
//    public List<ServerInfo<NettyClient>> getAvailableServerInfoList(){
//
//
//    }

    @Override
    public void onRegister(ServiceInstance<ServerInfo<NettyClient>> serviceInstance) {

    }

    @Override
    public void onUnRegister(ServiceInstance<ServerInfo<NettyClient>> serviceInstance) {

    }

    @Override
    public void onUpdate(ServiceInstance<ServerInfo<NettyClient>> serviceInstance) {

    }
}
