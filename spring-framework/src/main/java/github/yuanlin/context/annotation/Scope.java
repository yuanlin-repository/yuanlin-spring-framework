package github.yuanlin.context.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标注 bean 的作用域（目前支持 singleton 和 prototype）
 *
 * @author yuanlin
 * @date 2022/02/05/20:53
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Scope {

    String scopeName() default "singleton";
}
