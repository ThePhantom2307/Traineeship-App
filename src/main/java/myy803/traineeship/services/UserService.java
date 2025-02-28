package myy803.traineeship.services;

import java.util.Optional;

import myy803.traineeship.model.User;
import myy803.traineeship.database.UserDatabase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService, UserDetailsService{

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private UserDatabase userDatabase;
	
	@Override
	public void saveUser(User user) {
		String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		userDatabase.save(user);
	}

	@Override
	public boolean isUserPresent(User user) {
		Optional<User> storedUser = userDatabase.findByUsername(user.getUsername());
		return storedUser.isPresent();
	}

	@Override
	public User findById(String username) {
		User userDetails = userDatabase.findByUsername(username).orElseThrow(
                ()-> new UsernameNotFoundException(
                        String.format("USER_NOT_FOUND", username)
                ));
		
		return userDetails;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetails userDetails = userDatabase.findByUsername(username).orElseThrow(
				()-> new UsernameNotFoundException(
						String.format("USER_NOT_FOUND %s", username)
				));
		
		return userDetails;
	}
	
}
