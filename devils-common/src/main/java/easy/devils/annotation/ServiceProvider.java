package easy.devils.annotation;

import easy.devils.common.DevilsConstant;
import easy.devils.protocol.HaStrategyType;
import easy.devils.protocol.LoadBalanceType;
import org.springframework.stereotype.Component;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface ServiceProvider {
    /**
     * 服务名称
     * @return
     */
    String serviceName() default "";

    /**
     * ha策略
     * @return
     */
    HaStrategyType haStrategyType() default HaStrategyType.FAIL_FAST;

    /**
     * 负载均衡策略
     * @return
     */
    LoadBalanceType loadBalanceType() default LoadBalanceType.RANDOM;

    /**
     * 超时时间(毫秒)
     * @return
     */
    int connectionTimeOut() default DevilsConstant.CONNECTION_TIME_OUT;

    /**
     * 指定主机列表
     * @return
     */
    String[] host() default {};

    /**
     * 所属组
     * @return
     */
    String group() default "default";

    /**
     * 最大线程数
     * @return
     */
    int maxThreadNum() default 1;
}
