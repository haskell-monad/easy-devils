package easy.devils.client.cluster;

import easy.devils.client.cluster.ha.AbstractHaStrategy;
import easy.devils.client.cluster.ha.FailFastHaStrategyImpl;
import easy.devils.client.cluster.ha.FailoverHaStrategyImpl;
import easy.devils.client.cluster.loadbalance.AbstractLoadBalance;
import easy.devils.client.cluster.loadbalance.HashLoadBalanceImpl;
import easy.devils.client.cluster.loadbalance.RandomLoadBalanceImpl;
import easy.devils.client.cluster.loadbalance.RandomWeightLoadBalanceImpl;
import easy.devils.client.cluster.loadbalance.RoundRobinLoadBalanceImpl;
import easy.devils.client.cluster.loadbalance.RoundRobinWeightLoadBalanceImpl;
import easy.devils.discovery.AbstractServiceDiscovery;
import easy.devils.protocol.HaStrategyType;
import easy.devils.protocol.LoadBalanceType;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;

/**
 * @author limengyu
 * @create 2017/12/14
 */
public class ClusterBuilder {

    public static AbstractHaStrategy builderHaStrategy(HaStrategyType haStrategyType,GenericKeyedObjectPoolConfig config){
        switch (haStrategyType){
            case FAIL_FAST:
                return new FailFastHaStrategyImpl(config);
            case FAIL_OVER:
                return new FailoverHaStrategyImpl(config);
            default:
                return new FailFastHaStrategyImpl(config);
        }
    }

    public static AbstractLoadBalance builderLoadBalance(LoadBalanceType loadBalanceType,
                                                         AbstractServiceDiscovery serviceDiscovery,
                                                         String serviceName,
                                                         FailoverCheckManager checkManager) {
        switch (loadBalanceType){
            case RANDOM:
                return new RandomLoadBalanceImpl<>(serviceDiscovery,serviceName,checkManager);
            case HASH:
                return new HashLoadBalanceImpl<>(serviceDiscovery,serviceName,checkManager);
            case ROBBIN:
                return new RoundRobinLoadBalanceImpl<>(serviceDiscovery,serviceName,checkManager);
            case WEIGHT:
                return new RoundRobinWeightLoadBalanceImpl<>(serviceDiscovery,serviceName,checkManager);
            case WEIGHT_RANDOM:
                return new RandomWeightLoadBalanceImpl<>(serviceDiscovery,serviceName,checkManager);
            default:
                return new RandomLoadBalanceImpl<>(serviceDiscovery,serviceName,checkManager);
        }

    }
}
