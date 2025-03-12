package myy803.traineeship.mappers;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import myy803.traineeship.model.Interest;

public interface InterestMapper extends JpaRepository<Interest, Integer> {
    Optional<Interest> findByName(String name);
}
