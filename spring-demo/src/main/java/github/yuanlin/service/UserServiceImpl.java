package github.yuanlin.service;

import github.yuanlin.model.User;

/**
 * @author yuanlin
 * @date 2022/02/09/20:45
 */
public class UserServiceImpl implements UserService {

	@Override
	public User createUser(String firstName, String lastName, int age) {
		User user = new User();
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setAge(age);
		return user;
	}

	@Override
	public User queryUser() {
		User user = new User();
		user.setFirstName("test");
		user.setLastName("test");
		user.setAge(20);
		return user;
	}

	@Override
	public void queryException() {
		System.out.println("执行 queryException 方法");
		throw new RuntimeException("执行 queryException 发生异常");
	}
}
