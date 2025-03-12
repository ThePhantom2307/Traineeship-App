package myy803.traineeship.services;

import java.util.Optional;

import myy803.traineeship.model.Interest;
import myy803.traineeship.model.Skill;

public interface IntInterestSkillService {
	Interest saveInterest(Interest interest);
	Skill saveSkill(Skill skill);
	Optional<Skill> findSkillByName(String name);
	Optional<Interest> findInterestByName(String name);
}
