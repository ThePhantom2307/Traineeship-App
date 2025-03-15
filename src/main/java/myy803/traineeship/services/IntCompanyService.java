package myy803.traineeship.services;

import myy803.traineeship.model.Company;
import myy803.traineeship.model.User;

public interface IntCompanyService {
	void saveCompany(Company company);
	Boolean isCompanyExists(String username);
	Company getCompany(User user);
}
