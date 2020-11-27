package com.demo.project.controllers;


import java.util.HashSet;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.demo.project.models.Employee;
import com.demo.project.models.Role;


import com.demo.project.repository.RoleRepository;
import com.demo.project.security.services.EmailService;
import com.demo.project.security.services.EmployeeService;

@RestController
public class superAdminController {
	@Autowired
	EmployeeService empService;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	EmailService emailService;
	
	//@Autowired
	//UserDetailsServiceImpl service;
	
	@PostMapping("/addAdmin")
	@PreAuthorize("hasRole('SUPERADMIN')")
	@ResponseStatus(HttpStatus.CREATED)
	public String addAdmin(@RequestBody Employee employee) {
		try {
			Set<Role> role = new HashSet<>();
			Role adminRole = roleRepository.findByName("ROLE_ADMIN")
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			role.add(adminRole);
			Role UserRole = roleRepository.findByName("ROLE_USER")
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			role.add(UserRole);
			employee.setRoles(role);
			String password = Employee.generatePassword();
			employee.setPassword(encoder.encode(password));
			empService.save(employee);
			return "Admin Saved Successfully With Password " + password;
		} catch (NoSuchElementException e) {
			return "NOT_FOUND";
		}
	}

	@PostMapping("/addSuperAdmin")
	@PreAuthorize("hasRole('SUPERADMIN')")
	@ResponseStatus(HttpStatus.CREATED)
	public String addSuperAdmin(@RequestBody Employee employee) {
		try {
			Set<Role> role = new HashSet<>();
			Role superAdminRole = roleRepository.findByName("ROLE_SUPERADMIN")
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			role.add(superAdminRole);
			Role UserRole = roleRepository.findByName("ROLE_USER")
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			role.add(UserRole);
			employee.setRoles(role);
			String password = Employee.generatePassword();
			employee.setPassword(encoder.encode(password));
			empService.save(employee);
			return "SuperAdmin Saved Successfully With Password " + password;
		} catch (NoSuchElementException e) {
			return "NOT_FOUND";
		}
	}

	@DeleteMapping("/deleteAdmin/{id}")
	@PreAuthorize("hasRole('SUPERADMIN')")
	@ResponseStatus(HttpStatus.CREATED)
	@Transactional
	public String deleteAdmin(@PathVariable String id) {
		try {
			empService.delete(id);
			return "Admin with Employee Id " + id + " Deleted!";
		} catch (NoSuchElementException e) {
			return "NOT_FOUND";
		}
	}

	@DeleteMapping("/deleteSuperAdmin/{id}")
	@PreAuthorize("hasRole('SUPERADMIN')")
	@ResponseStatus(HttpStatus.CREATED)
	@Transactional
	public String deleteSuperAdmin(@PathVariable String id) {
		try {
			empService.delete(id);
			return "SuperAdmin with Employee Id " + id + " Deleted!";
		} catch (NoSuchElementException e){
			return "NOT_FOUND";
		}
	}
	
	@PutMapping("/updateAdmin/{id}")
	@PreAuthorize("hasRole('SUPERADMIN')")
	@ResponseStatus(HttpStatus.CREATED)
	public String updateAdmin(@RequestBody Employee employee, @PathVariable String id) {
			Set<Role> role = new HashSet<>();
			Role adminRole = roleRepository.findByName("ROLE_ADMIN")
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			role.add(adminRole);
			employee.setRoles(role);
			employee.setEmpID(id);
			empService.save(employee);
			return "Admin Updates Successfully!!";
	}
	
	@PutMapping("/updateSuperAdmin/{id}")
	@PreAuthorize("hasRole('SUPERADMIN')")
	@ResponseStatus(HttpStatus.CREATED)
	public String updateSuperAdmin(@RequestBody Employee employee, @PathVariable String id) {
			Set<Role> role = new HashSet<>();
			Role adminRole = roleRepository.findByName("ROLE_SUPERADMIN")
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			role.add(adminRole);
			employee.setRoles(role);
			employee.setEmpID(id);
			empService.save(employee);
			return "SuperAdmin Updates Successfully!!";
	}
	
	

	
	@PutMapping("/changePassword/{empID}")
	@PreAuthorize("hasRole('ROLE_SUPERADMIN')")
	@ResponseStatus(HttpStatus.CREATED)
	public  String changePassword(@PathVariable String password, @PathVariable String empID) {
			Optional<Employee> emp = empService.findByID(empID) ;
			Employee employee = emp.get();
			employee.setPassword(encoder.encode(password));
			empService.save(employee);
			return "password Updated";
	}
	
	
	
	
	@GetMapping("/adminDetails")
	@PreAuthorize("hasRole('SUPERADMIN')")
	@ResponseStatus(HttpStatus.CREATED)
	public List<String> getAdmin() {
		return empService.findAdminDetails();
		
	}
	@GetMapping("/superAdminDetails")
	@PreAuthorize("hasRole('SUPERADMIN')")
	@ResponseStatus(HttpStatus.CREATED)
	public List<String> getSuperAdmin() {
		return empService.findSuperAdminDetails();
		
	}



}
