package github.yuanlin.beans.factory.lifecycle;

/**
 * @author yuanlin
 * @date 2022/02/05/20:31
 */
public interface InitializingBean {

    void afterPropertiesSet() throws Exception;
}
