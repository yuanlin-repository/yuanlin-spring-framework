package github.yuanlin.aop;

/**
 * 用于检查当前类是否是目标类
 *
 * @author yuanlin
 * @date 2022/02/11/11:52
 */
public interface ClassFilter {

    /**
     * 当前切面是否适用于 clazz
     * @param clazz 要进行适配的类
     * @return 当前切面是否适用于 clazz
     */
    boolean matches(Class<?> clazz);
}
