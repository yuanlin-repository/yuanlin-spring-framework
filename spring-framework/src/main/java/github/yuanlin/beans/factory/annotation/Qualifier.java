package github.yuanlin.beans.factory.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于 @Autowire 注入时接口有多个实现类的情况（指定注入的实现类 name）
 *
 * @author yuanlin
 * @date 2022/02/08/15:51
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Qualifier {

    String value() default "";
}
