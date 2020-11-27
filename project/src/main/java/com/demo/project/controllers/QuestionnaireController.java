package com.demo.project.controllers;

import java.util.Dictionary;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.demo.project.models.Employee;

import com.demo.project.models.Questionnaire;
import com.demo.project.models.Role;
import com.demo.project.repository.RoleRepository;
import com.demo.project.repository.UserRepository;
import com.demo.project.security.services.EmailService;
import com.demo.project.security.services.EmployeeService;
import com.demo.project.security.services.MappingService;
import com.demo.project.security.services.QuestionnaireService;

@RestController
public class QuestionnaireController {
	
	@Autowired
	EmployeeService empService;

	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	EmailService emailService;
	
	@Autowired
	QuestionnaireService questionnaireService;
	
	@Autowired
	MappingService mapService;
	
	
	@GetMapping("/Questionnaire")
	@PreAuthorize("hasRole('SUPERADMIN') or hasRole('ADMIN')")
	@ResponseStatus(HttpStatus.CREATED)
	public List<String> findAllQuestionnaire() {

		return questionnaireService.findQuestionnaire();
		
	}
	
	@GetMapping("/QuestionnaireDetails/{queTitle}")
	@PreAuthorize("hasRole('SUPERADMIN') or hasRole('ADMIN')")
	@ResponseStatus(HttpStatus.CREATED)
	public Questionnaire questionnaireDetails(@PathVariable String queTitle) {
		Optional<Questionnaire> quesDetail = questionnaireService.findForDeatils(queTitle);
		Questionnaire question = quesDetail.get();
		return question;
	}

	
	@PostMapping("/saveQuestionnaire")
	@PreAuthorize("hasRole('SUPERADMIN') or hasRole('ADMIN')")
	@ResponseStatus(HttpStatus.CREATED)
	public String addQuestionnaire(@RequestBody Questionnaire questionnaire) {
		try {
			questionnaireService.save(questionnaire);	
		} catch(NoSuchElementException e) {
			return "not Found" + e;
			
		}
		return "questionnaire added";
		}

	@PostMapping("/publish/{QuestionnaireID}")
	@PreAuthorize("hasRole('SUPERADMIN') or hasRole('ADMIN')")
	@ResponseStatus(HttpStatus.CREATED)
	public String publishQuestionnaire(@RequestBody List<Employee> employeeList, @PathVariable int QuestionnaireID) {
		try {
			
			Optional<Questionnaire> questionnaire = questionnaireService.findByID(QuestionnaireID);
			Questionnaire questionnaireData = questionnaire.get();
			Dictionary<String, String> dict = new Hashtable<String, String>();
		
			
			for(Employee employee : employeeList) {
				String givenID = employee.getEmpID();
				if(userRepository.existsByEmpID(givenID)) {
					
					mapService.getData(questionnaireData.getId(),employee.getEmpID(),0);	
				}
				else {
					Set<Role> role = new HashSet<>();
					Role adminRole = roleRepository.findByName("ROLE_USER")
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					role.add(adminRole);
					employee.setRoles(role);
					String password = Employee.generatePassword();
					employee.setPassword(encoder.encode(password));
					empService.save(employee);
					dict.put(employee.getUsername(), password);
					
					mapService.getData(questionnaireData.getId(),employee.getEmpID(),0);
				
					
					String mailSubject = "Comapny Policy agreement to  "+employee.getUsername();
					String mailText = "Hello "+ employee.getUsername() + "\n" 
							+ questionnaireData.getMailBody() + "please login using\n" 
							+"username : " + employee.getUsername() +"\n" +
							"password : " + employee.getPassword()+"\n Thank You";
					
					emailService.sendEmail(employee, mailSubject, mailText);
						
					}
				}
				return "user Saved Successfully With Password " + dict;
				}
		catch(NoSuchElementException e) {
		    	return "NOT_FOUND";
		    }
		}
	@PostMapping("/remind/{QuestionnaireID}")
	@PreAuthorize("hasRole('SUPERADMIN') or hasRole('ADMIN')")
	@ResponseStatus(HttpStatus.CREATED)
	public String RemindQuestionnaire(@PathVariable int QuestionnaireID) {
		try {
			Optional<Questionnaire> questionnaire = questionnaireService.findByID(QuestionnaireID);
			Questionnaire questionnaireData = questionnaire.get();
			List<Employee> remindEmpID = mapService.findForRemindAction(QuestionnaireID);
			for(Employee employee : remindEmpID) {
				String mailSubject = "Reminder for Comapny Policy agreement to  "+employee.getUsername();
				String mailText = "Hello "+ employee.getUsername() + "\n" 
						+ questionnaireData.getMailBody() + "\nplease login using\n" 
						+"username : " + employee.getUsername() +"\n" +
						"password : Previous Used" +"\n if you can't remember use forgot password\n Thank You";
				emailService.sendEmail(employee, mailSubject, mailText);	
			}
			
		}
		catch(Exception e) {
			return "not found";
		}
		return "reminded";
	}
	
	
	@GetMapping("/generateReport/{QuestionnaireID}")
	@PreAuthorize("hasRole('SUPERADMIN') or hasRole('ADMIN')")
	@ResponseStatus(HttpStatus.CREATED)
	public List<String> generateReport(@PathVariable int QuestionnaireID) {
		List<String> report = mapService.getGeneratedReport(QuestionnaireID);
		return report;
	}
	
}
