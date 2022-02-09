package github.yuanlin.beans;

import github.yuanlin.beans.exception.BeansException;
import github.yuanlin.beans.factory.config.PropertyValue;
import github.yuanlin.beans.factory.config.PropertyValues;

/**
 * 属性注入功能接口
 *
 * @author yuanlin
 * @date 2022/02/09/14:01
 */
public interface PropertyAccessor {

    void setPropertyValue(PropertyValue pv) throws BeansException;

    void setPropertyValues(PropertyValues pvs) throws BeansException;

    Class<?> getPropertyType(String propertyName) throws BeansException;

    Object getPropertyValue(String propertyName) throws BeansException;
}
