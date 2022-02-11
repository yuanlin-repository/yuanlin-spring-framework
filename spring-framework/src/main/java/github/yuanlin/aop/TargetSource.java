package github.yuanlin.aop;

/**
 * @author yuanlin
 * @date 2022/02/11/12:33
 */
public interface TargetSource {

    /**
     * 获取目标类的 Class 对象
     * @return 返回目标类
     */
    Class<?> getTargetClass();

    /**
     * 获取目标对象
     * @return 目标对象
     * @throws Exception 获取失败返回异常
     */
    Object getTarget() throws Exception;
}
