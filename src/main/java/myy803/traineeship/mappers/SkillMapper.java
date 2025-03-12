package myy803.traineeship.mappers;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import myy803.traineeship.model.Skill;

public interface SkillMapper extends JpaRepository<Skill, Integer> {
    Optional<Skill> findByName(String name);
}

