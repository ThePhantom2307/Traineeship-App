package myy803.traineeship.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import myy803.traineeship.dao.CompanyDAO;
import myy803.traineeship.model.Company;
import myy803.traineeship.model.User;

public class CompanyService implements IntCompanyService {
	@Autowired
	private CompanyDAO companyDAO;

	@Override
	public void saveCompany(Company company) {
		companyDAO.save(company);
	}

	@Override
	public Boolean isCompanyExists(String username) {
		Optional<Company> company = companyDAO.findByUsername(username);
		if (company.isPresent()) {
			return true;
		}
		return false;
	}
	
	@Override
	public Company getCompany(User user) {
		String username = user.getUsername();
		Optional<Company> optCompany = companyDAO.findByUsername(username);
	    Company company;
	    
	    if (optCompany.isPresent()) {
	    	company = optCompany.get();
	    } else {
	    	company = new Company();
	    	company.setUsername(username);
	    	company.setCompanyName("");
	    	company.setLocation("");
	    }
	    
		return company;
	}
}
