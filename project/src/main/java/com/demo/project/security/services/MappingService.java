package com.demo.project.security.services;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.project.models.Employee;
import com.demo.project.models.Mapping;

import com.demo.project.repository.MappingRepository;

@Service
public class MappingService {
	
	@Autowired
	MappingRepository mapRepo;
	
	public void save(Mapping mapping) {
		mapRepo.save(mapping);
	}
	public void getData(int queId, String empId, int status) {
		Mapping map = new Mapping(queId,empId,status);
		mapRepo.save(map);
		
	}
	
	public Optional<Mapping> findByempID(String empID) {
		return mapRepo.findByempId(empID);
	}
	
	public Optional<Mapping> findByqueID(int queID) {
		return mapRepo.findByqueId(queID);
	}
	
	public Optional<Mapping> findByTwoValues(int queID, String empID) {
		return mapRepo.findByTwoValues(queID, empID);
	}

	public List<String> findCompletedAction(String empID) {
		return mapRepo.findCompletedAction(empID);
	}
	
	public List<String> findPendingAction(String empID) {
		return mapRepo.findPendingAction(empID);
	}
	
	public List<Employee> findForRemindAction(int queId) {
		return mapRepo.findForRemindAction(queId);
	}
	public List<String> getGeneratedReport(int queID) {
		return mapRepo.generateReport(queID);
	}

}
