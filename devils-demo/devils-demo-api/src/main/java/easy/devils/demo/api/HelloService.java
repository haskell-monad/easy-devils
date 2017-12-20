package easy.devils.demo.api;

import easy.devils.annotation.MethodProvider;
import easy.devils.annotation.ServiceProvider;
import easy.devils.common.DevilsConstant;
import easy.devils.protocol.CompressType;
import easy.devils.protocol.HaStrategyType;
import easy.devils.protocol.LoadBalanceType;
import easy.devils.protocol.SerializeType;

/**
 * @author limengyu
 * @create 2017/11/18
 */
@ServiceProvider(
        serviceName = "helloService",
        haStrategyType = HaStrategyType.FAIL_FAST,
        loadBalanceType = LoadBalanceType.RANDOM,
        group = "v1.1.0",
        host = {"192.168.0.1:2181","192.168.0.2:2181"},
        connectionTimeOut = DevilsConstant.CONNECTION_TIME_OUT)
public interface HelloService {

    @MethodProvider(
            methodName = "sayHello",
            serialize = SerializeType.HESSIAN2,
            compress = CompressType.NONE,
            timeout = DevilsConstant.CLIENT_TIME_OUT)
    String sayHello(String name);

    @MethodProvider(
            methodName = "whois",
            serialize = SerializeType.HESSIAN2,
            compress = CompressType.GZIP,
            timeout = DevilsConstant.CLIENT_TIME_OUT)
    String whois(String name);
}
