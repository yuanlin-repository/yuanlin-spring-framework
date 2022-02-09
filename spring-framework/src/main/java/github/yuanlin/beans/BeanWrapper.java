package github.yuanlin.beans;

import github.yuanlin.beans.exception.BeansException;
import github.yuanlin.beans.factory.config.PropertyValue;
import github.yuanlin.beans.factory.config.PropertyValues;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author yuanlin
 * @date 2022/02/05/21:52
 */
@Slf4j
public class BeanWrapper implements PropertyAccessor {

    private Object wrappedObject;

    private Class<?> beanClass;

    public BeanWrapper(Object wrappedObject) {
        this.wrappedObject = wrappedObject;
    }

    public Object getWrappedInstance() {
        return this.wrappedObject;
    }

    public Class<?> getWrappedClass() {
        return getWrappedInstance().getClass();
    }

    @Override
    public void setPropertyValue(PropertyValue pv) throws BeansException {
        Class<?> wrappedClass = getWrappedClass();
        try {
            Field field = wrappedClass.getDeclaredField(pv.getName());
            field.setAccessible(true);
            field.set(getWrappedInstance(), pv.getValue());
        } catch (NoSuchFieldException e) {
            log.error("bean[" + wrappedClass.getName() +"] dont't have a property value named: [{}]", pv.getName(), e);
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            log.error("bean[" + wrappedClass.getName() +"] set property error");
            e.printStackTrace();
        }
    }

    @Override
    public void setPropertyValues(PropertyValues pvs) throws BeansException {
        List<PropertyValue> propertyValues = pvs.getPropertyValues();
        for (PropertyValue propertyValue : propertyValues) {
            setPropertyValue(propertyValue);
        }
    }

    @Override
    public Class<?> getPropertyType(String propertyName) throws BeansException {
        return null;
    }

    @Override
    public Object getPropertyValue(String propertyName) throws BeansException {
        return null;
    }
}
