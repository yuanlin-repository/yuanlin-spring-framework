package github.yuanlin.beans.factory.config;

/**
 * 单个键值对 key -> value : fieldName -> injectValue
 *
 * @author yuanlin
 * @date 2022/02/06/17:49
 */
public class PropertyValue {

    private final String name;
    private final Object value;

    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }

}
