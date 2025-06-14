package myy803.traineeship.services;

import java.util.Optional;

import myy803.traineeship.dao.UserDAO;
import myy803.traineeship.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService, UserDetailsService{

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private UserDAO userDAO;
	
	@Override
	public void saveUser(User user) {
		String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		userDAO.save(user);
	}
	
	@Override
	public User authenticateAndGetUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		Optional<User> optUser = userDAO.findByUsername(username);
		User user = optUser.get();
		return user;
	}

	@Override
	public boolean isUserPresent(User user) {
		Optional<User> storedUser = userDAO.findByUsername(user.getUsername());
		return storedUser.isPresent();
	}

	@Override
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDAO.findByUsername(username).orElseThrow(
				()-> new UsernameNotFoundException(
						String.format("USER_NOT_FOUND %s", username)
				));
		
		return user;
	}
}
