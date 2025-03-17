package myy803.traineeship.services;

import myy803.traineeship.model.User;

public interface UserService {
	void saveUser(User user);
	boolean isUserPresent(User user);
	User authenticateAndGetUser();
}
