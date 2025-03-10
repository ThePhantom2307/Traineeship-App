package myy803.traineeship.services;

import myy803.traineeship.model.User;

public interface IUserService {
	void saveUser(User user);
	User findById(String username);
	boolean isUserPresent(User user);
}
