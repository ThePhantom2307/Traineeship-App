package myy803.traineeship.services;

import myy803.traineeship.model.User;

public interface IntUserService {
	void saveUser(User user);
	boolean isUserPresent(User user);
	User findById(Integer userId);
	int authenticateAndGetUserId();
}
