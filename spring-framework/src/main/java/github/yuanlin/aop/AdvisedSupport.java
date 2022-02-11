package github.yuanlin.aop;

import github.yuanlin.aop.advisor.Advisor;
import github.yuanlin.aop.targetsource.EmptyTargetSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * 管理 AOP 代理配置信息
 *
 * @author yuanlin
 * @date 2022/02/11/12:09
 */
public class AdvisedSupport {

    public static final TargetSource EMPTY_TARGET_SOURCE = EmptyTargetSource.INSTANCE;

    /**
     * bean 实例，默认为空
     */
    TargetSource targetSource = EMPTY_TARGET_SOURCE;

    /**
     * 存储当前 bean 的所有接口
     */
    private List<Class<?>> interfaces = new ArrayList<>();

    /**
     * 存储可以增强当前 bean 的所有 advisor
     */
    private List<Advisor> advisors = new ArrayList<>();

    public void addAdvisors(Collection<Advisor> advisors) {
        if (advisors != null && !advisors.isEmpty()) {
            for (Advisor advisor : advisors) {
                this.advisors.add(advisor);
            }
        }
    }

    public void addAdvisors(Advisor... advisors) {
        addAdvisors(Arrays.asList(advisors));
    }

    public void addInterface(Class<?> ifc) {
        this.interfaces.add(ifc);
    }

    public void setTargetSource(TargetSource targetSource) {
        this.targetSource = (targetSource != null ? targetSource : EMPTY_TARGET_SOURCE);
    }

    public Class<?>[] getProxiedInterfaces() {
        Class<?>[] clazz = new Class<?>[interfaces.size()];
        return this.interfaces.toArray(clazz);
    }

    public boolean isInterfacesEmpty() {
        return this.interfaces.isEmpty();
    }
}
