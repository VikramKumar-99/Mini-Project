package com.demo.project.security.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.demo.project.models.Employee;
import com.demo.project.repository.UserRepository;


@Service
public class EmployeeService {
	
	@Autowired
	private UserRepository repo;
	
	public List<Employee> listAll(){
		return repo.findAll();
	}
	
	public void save(Employee employee) {
		repo.save(employee);
	}
	
	public Employee get(String id) {
		return repo.getOne(id);
	}
	
	public void delete(String id) {
		repo.deleteById(id);
	}

	public Optional<Employee> findByEmail(String mail) {
		return repo.findByEmail(mail);
	}
	public Optional<Employee> findByID(String empID) {
		return repo.findById(empID);
	}

	public List<String> findAdminDetails() {
		// TODO Auto-generated method stub
		return repo.findAdminDetails();
	}

	public List<String> findSuperAdminDetails() {
		// TODO Auto-generated method stub
		return repo.findSuperAdminDetails();
	}

}
