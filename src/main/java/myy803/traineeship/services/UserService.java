package myy803.traineeship.services;

import java.util.Optional;

import myy803.traineeship.mappers.UserMapper;
import myy803.traineeship.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IntUserService, UserDetailsService{

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public void saveUser(User user) {
		String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		userMapper.save(user);
	}
	
	@Override
	public int authenticateAndGetUserId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		Optional<User> optUser = userMapper.findByUsername(username);
		User user = optUser.get();
		int userID = user.getUserID();
		return userID;
	}

	@Override
	public boolean isUserPresent(User user) {
		Optional<User> storedUser = userMapper.findByUsername(user.getUsername());
		return storedUser.isPresent();
	}

	@Override
	public User findById(Integer userId) {
		User userDetails = userMapper.findById(userId).orElseThrow(
                ()-> new UsernameNotFoundException(
                        String.format("USER_NOT_FOUND", userId)
                ));
		
		return userDetails;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDetails userDetails = userMapper.findByUsername(username).orElseThrow(
				()-> new UsernameNotFoundException(
						String.format("USER_NOT_FOUND %s", username)
				));
		
		return userDetails;
	}
}
