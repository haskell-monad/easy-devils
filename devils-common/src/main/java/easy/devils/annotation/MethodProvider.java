package easy.devils.annotation;

import easy.devils.common.DevilsConstant;
import easy.devils.protocol.CompressType;
import easy.devils.protocol.SerializeType;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MethodProvider {

    String methodName() default "";
    long timeout() default DevilsConstant.CLIENT_TIME_OUT;
    SerializeType serialize() default SerializeType.ProtoBuf;
    CompressType compress() default CompressType.SNAPPY;
}
