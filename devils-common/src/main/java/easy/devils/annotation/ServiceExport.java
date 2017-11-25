package easy.devils.annotation;

import easy.devils.common.DevilsConstant;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 暴露服务
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ServiceExport {

    /**
     * 暴露服务所在端口
     * @return
     */
    int port() default DevilsConstant.SERVICE_DEFAULT_PORT;
}
