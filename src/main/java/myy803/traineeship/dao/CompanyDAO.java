package myy803.traineeship.dao;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import myy803.traineeship.model.Company;

@Repository
public interface CompanyDAO extends JpaRepository<Company, String> {
    Optional<Company> findByUsername(String username);
}
