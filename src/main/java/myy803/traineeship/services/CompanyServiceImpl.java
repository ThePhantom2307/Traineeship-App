package myy803.traineeship.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import myy803.traineeship.dao.CompanyDAO;
import myy803.traineeship.model.Company;

@Service
public class CompanyServiceImpl implements CompanyService {
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
	public Company getCompanyByUsername(String username) {
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
