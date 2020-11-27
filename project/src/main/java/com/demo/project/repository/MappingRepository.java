package com.demo.project.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.demo.project.models.Employee;
import com.demo.project.models.Mapping;

@Repository
public interface MappingRepository extends JpaRepository<Mapping, Integer> {
	Optional<Mapping> findById(int id);
	
	Optional<Mapping> findByempId(String empId);
	Optional<Mapping> findByqueId(int queId);

	@Query(value = "SELECT * FROM ques_user WHERE que_id = ?1 and emp_id = ?2", nativeQuery = true)
	Optional<Mapping> findByTwoValues(Integer que_id, String emp_id);
	
	
	@Query(value = "SELECT title FROM project.questionnaires WHERE queid in"
			+ "(SELECT que_id from project.ques_user where accepted = 1 and emp_id=?1)", nativeQuery = true)
	 List<String> findCompletedAction(String empId);

	@Query(value = "SELECT title FROM project.questionnaires WHERE queid in"
			+ "(SELECT que_id from project.ques_user where accepted = 0 and emp_id=?1 )", nativeQuery = true)
	 List<String> findPendingAction(String empId);
	
	@Query(value = "SELECT empid FROM project.employee WHERE empid in"
			+ "(SELECT emp_id from project.ques_user where accepted = 0 and que_id=?1 )", nativeQuery = true)
	 List<Employee> findForRemindAction(int queId);
	
	@Query(value = "SELECT emp.empid, emp.name, emp.email, queUser.accepted"
			+ "FROM project.employee emp, project.ques_user queUser"
			+ "WHERE emp.empid = queUser.emp_id and queUser.que_id = ?1", nativeQuery = true)
	List<String> generateReport(int queID);

}
