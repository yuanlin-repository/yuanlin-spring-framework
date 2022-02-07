package github.yuanlin.beans.factory.config;

/**
 * bean 的 ref 类型的注入属性定义
 *
 * @author yuanlin
 * @date 2022/02/07/16:12
 */
public class RuntimeBeanReference {

    private String name;

    public RuntimeBeanReference(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
