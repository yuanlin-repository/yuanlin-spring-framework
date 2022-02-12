package github.yuanlin.aop.targetsource;

import github.yuanlin.aop.TargetSource;

/**
 * 空目标对象
 *
 * @author yuanlin
 * @date 2022/02/11/17:27
 */
public final class EmptyTargetSource implements TargetSource {

    public static final EmptyTargetSource INSTANCE = new EmptyTargetSource(null);

    private final Class<?> targetClass;

    public EmptyTargetSource(Class<?> targetClass) {
        this.targetClass = targetClass;
    }

    @Override
    public Class<?> getTargetClass() {
        return this.targetClass;
    }

    @Override
    public Object getTarget() throws Exception {
        return null;
    }
}
