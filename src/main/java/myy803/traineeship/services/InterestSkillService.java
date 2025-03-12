package myy803.traineeship.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import myy803.traineeship.mappers.InterestMapper;
import myy803.traineeship.mappers.SkillMapper;
import myy803.traineeship.model.Interest;
import myy803.traineeship.model.Skill;

@Service
public class InterestSkillService implements IntInterestSkillService{
	@Autowired
	private InterestMapper interestMapper;
	
	@Autowired
	private SkillMapper skillMapper;
	
	@Override
	public Interest saveInterest(Interest interest) {
		return interestMapper.save(interest);
	}
	
	@Override
	public Skill saveSkill(Skill skill) {
		return skillMapper.save(skill);
	}
	
	@Override
	public Optional<Skill> findSkillByName (String name) {
		return skillMapper.findByName(name);
	}
	
	@Override
	public Optional<Interest> findInterestByName (String name) {
		return interestMapper.findByName(name);
	}
}
