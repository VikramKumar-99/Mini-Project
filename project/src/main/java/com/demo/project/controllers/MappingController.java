package com.demo.project.controllers;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PutMapping;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import com.demo.project.models.Mapping;
import com.demo.project.repository.MappingRepository;
import com.demo.project.security.services.MappingService;
import com.demo.project.security.services.QuestionnaireService;


@RestController
public class MappingController {
	
	@Autowired
	QuestionnaireService questionnaireService;
	
	@Autowired
	MappingService mapService;
	
	@Autowired
	MappingRepository mapRepo;
	

	
	@PutMapping("/acceptStatus/{QuestionnaireID}/{employeeID}")
	@PreAuthorize("hasRole('USER')")
	@ResponseStatus(HttpStatus.CREATED)
	public String acceptStatus(@PathVariable String employeeID, @PathVariable int QuestionnaireID) {
		try {
			Optional<Mapping> mapData =  mapService.findByTwoValues(QuestionnaireID, employeeID);
			Mapping map = mapData.get();
			map.setAccept(1);
			mapRepo.save(map);	
				
		} catch(NoSuchElementException e) {
			return "not Found" + e;
			
		}
		return "Accepted  by user";
		}
	
	
	@GetMapping("/completedAction/{empId}")
	@PreAuthorize("hasRole('USER')")
	@ResponseStatus(HttpStatus.CREATED)
	public List<String> completedAction(@PathVariable String empId) {

		return mapService.findCompletedAction(empId);
		
	}
	
	@GetMapping("/pendingAction/{empId}")
	@PreAuthorize("hasRole('USER')")
	@ResponseStatus(HttpStatus.CREATED)
	public List<String> pendingAction(@PathVariable String empId) {

		return mapService.findPendingAction(empId);
		
	}
}
