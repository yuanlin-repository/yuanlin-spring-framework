package github.yuanlin.aop.targetsource;

import github.yuanlin.aop.TargetSource;

/**
 * 单例目标对象
 *
 * @author yuanlin
 * @date 2022/02/11/16:54
 */
public class SingletonTargetSource implements TargetSource {

    private final Object target;

    public SingletonTargetSource(Object target) {
        this.target = target;
    }

    @Override
    public Class<?> getTargetClass() {
        return this.target.getClass();
    }

    @Override
    public Object getTarget() {
        return this.target;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SingletonTargetSource)) {
            return false;
        }
        SingletonTargetSource otherTargetSource = (SingletonTargetSource) other;
        return this.target.equals(otherTargetSource.target);
    }

    public int hashCode() {
        return this.target.hashCode();
    }

    @Override
    public String toString() {
        return target.toString();
    }
}
