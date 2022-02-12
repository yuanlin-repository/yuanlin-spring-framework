package github.yuanlin.service;

import github.yuanlin.model.User;

/**
 * @author yuanlin
 * @date 2022/02/09/20:44
 */
public interface UserService {

	User createUser(String firstName, String lastName, int age);

	User queryUser();

	void queryException();
}
