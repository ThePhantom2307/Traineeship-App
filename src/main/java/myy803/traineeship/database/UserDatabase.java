package myy803.traineeship.database;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import myy803.traineeship.model.User;

public interface UserDatabase extends JpaRepository<User, Integer>{
	Optional<User> findByUsername(String username);
}
