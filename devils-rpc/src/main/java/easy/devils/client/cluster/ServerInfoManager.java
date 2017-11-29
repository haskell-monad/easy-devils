package easy.devils.client.cluster;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.curator.x.discovery.ServiceInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

import easy.devils.discovery.AbstractServiceDiscovery;
import easy.devils.discovery.AbstractServiceEventListener;
import easy.devils.protocol.MetaInfo;
import easy.devils.protocol.ServerInfo;

/**
 * 单个服务的管理
 * @author limengyu
 * @create 2017/11/28
 */
public class ServerInfoManager<T> extends AbstractServiceEventListener {
	private static final Logger LOGGER = LoggerFactory.getLogger(ServerInfoManager.class);
	private volatile List<ServerInfo<T>> cacheServerInstanceList = Lists.newArrayList();
	private AbstractServiceDiscovery serviceDiscovery;
	private String serviceName;
	private FailoverCheckManager failoverCheckManager;

	public ServerInfoManager(AbstractServiceDiscovery serviceDiscovery, String serviceName, FailoverCheckManager failoverCheckManager) {
		this.serviceDiscovery = serviceDiscovery;
		this.serviceName = serviceName;
		this.failoverCheckManager = failoverCheckManager;
		this.serviceDiscovery.subscribeService(serviceName, this);
		init();
	}
	/**
	 * 从注册中心拉取服务实例，缓存到本地， 同时注册该服务监听，当该服务实例有变化时，会收到通知，
	 * 然后执行相应监听方法@onRegister/@onUnRegister/onUnRegister
	 */
	public void init() {
		this.cacheServiceInstanceList();
	}
	/**
	 * 从注册中心拉取服务实例数据
	 */
	public void cacheServiceInstanceList() {
		Collection<ServiceInstance<MetaInfo>> instanceList;
		try {
			instanceList = serviceDiscovery.queryServiceInstance(serviceName);
		} catch (Exception e) {
			instanceList = new ArrayList<>();
			LOGGER.error("query service instance error: {}", e);
		}
		if (instanceList == null || instanceList.size() == 0) {
			return;
		}
		List<ServerInfo<T>> result = Lists.newArrayList();
		for (ServiceInstance<MetaInfo> serviceInstance : instanceList) {
			result.add(new ServerInfo<>(serviceInstance));
		}
		Collections.shuffle(result);
		this.cacheServerInstanceList = result;
	}
	/**
	 * 获取可使用服务列表
	 * @return
	 */
	public List<ServerInfo<T>> getAvailableServerInfoList() {
		List<ServerInfo<T>> result = new ArrayList<>();
		if (cacheServerInstanceList == null || cacheServerInstanceList.size() == 0) {
			// 本地缓存没有，需要从注册中心拉取服务数据
			init();
		}
		Set<ServerInfo<T>> failList = failoverCheckManager.failList();
		for (ServerInfo<T> serverInfo : cacheServerInstanceList) {
			if (!failList.contains(serverInfo)) {
				result.add(serverInfo);
			}
		}
		if (result.size() == 0) {
			// 本地、注册中心都没有的话，则使用之前记录失败的节点
			result.addAll(failList);
		}
		return result;
	}
	@Override
	public void onRegister(ServiceInstance serviceInstance) {
		init();
	}
	@Override
	public void onUnRegister(ServiceInstance serviceInstance) {
		init();
	}
	@Override
	public void onUpdate(ServiceInstance serviceInstance) {
		init();
	}
    public String getServiceName() {
        return serviceName;
    }
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
    public AbstractServiceDiscovery getServiceDiscovery() {
        return serviceDiscovery;
    }
    public void setServiceDiscovery(AbstractServiceDiscovery serviceDiscovery) {
        this.serviceDiscovery = serviceDiscovery;
    }
}
