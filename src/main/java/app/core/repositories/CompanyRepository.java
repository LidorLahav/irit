package app.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import app.core.entities.Company;

public interface CompanyRepository extends JpaRepository<Company, Integer>{
	
	boolean existsByName(String name);
	
	boolean existsByEmail(String email);
	
	Company findFirstById(int id);
	
	Company findFirstByEmailAndPassword(String email, String password);
	
	Company findFirstByEmail(String email);
	
}
