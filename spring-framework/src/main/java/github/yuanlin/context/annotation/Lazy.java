package github.yuanlin.context.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标注 bean 是否懒加载
 *
 * @author yuanlin
 * @date 2022/02/05/20:53
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Lazy {

    boolean value() default true;
}
