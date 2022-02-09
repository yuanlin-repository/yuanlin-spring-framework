package github.yuanlin.service;

import github.yuanlin.beans.factory.FactoryBean;
import github.yuanlin.context.stereotype.Component;
import github.yuanlin.model.Student;

/**
 * FactoryBean 接口测试类
 *
 * @author yuanlin
 * @date 2022/02/09/15:16
 */
@Component
public class TestFactoryBean implements FactoryBean<Student> {

    @Override
    public Student getObject() throws Exception {
        return new Student("小明", 15);
    }

    @Override
    public Class<?> getObjectType() {
        return Student.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
