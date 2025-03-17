package myy803.traineeship.services;

import myy803.traineeship.model.Company;

public interface CompanyService {
	void saveCompany(Company company);
	Boolean isCompanyExists(String username);
	Company getCompany(String string);
}
