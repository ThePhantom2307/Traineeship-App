package myy803.traineeship.dao;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import myy803.traineeship.model.User;

@Repository
public interface UserDAO extends JpaRepository<User, Integer> {
	Optional<User> findByUsername(String username);
}
