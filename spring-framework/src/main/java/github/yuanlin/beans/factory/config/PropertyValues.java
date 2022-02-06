package github.yuanlin.beans.factory.config;

import java.util.ArrayList;
import java.util.List;

/**
 * 表示注入对象的属性的键值对组
 *
 * @author yuanlin
 * @date 2022/02/06/17:49
 */
public class PropertyValues {

    private final List<PropertyValue> propertyValueList = new ArrayList<>(0);

    public void addPropertyValue(PropertyValue propertyValue) {
        propertyValueList.add(propertyValue);
    }

    public List<PropertyValue> getPropertyValues() {
        return propertyValueList;
    }
}
