package com.demo.project.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.demo.project.models.Employee;





@Repository
public interface UserRepository  extends JpaRepository<Employee, String> {
	Optional<Employee> findByName(String name);
	Optional<Employee> findByEmail(String email);

	
	@Query(value = "SELECT empid,name,email FROM project.employee WHERE empid in"
			+ "(SELECT employee_id from project.employee_role where role_id = 2)", nativeQuery = true)
	 List<String> findAdminDetails(); 
	
	
	
	@Query(value = "SELECT empid,name,email FROM project.employee WHERE empid in"
			+ "(SELECT employee_id from project.employee_role where role_id = 3)", nativeQuery = true)
	 List<String> findSuperAdminDetails();
	
	

	
	Boolean existsByEmpID(String empID); 

	Boolean existsByName(String name);

	Boolean existsByEmail(String email); 

}
