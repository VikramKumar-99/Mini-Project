package com.demo.project.security.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.project.models.Questionnaire;
import com.demo.project.repository.QuestionnaireRepository;

@Service
public class QuestionnaireService {
	
	@Autowired
	private QuestionnaireRepository QueRepo;
	
	public Questionnaire save(Questionnaire questionnaire) {
		return QueRepo.save(questionnaire);
	}
	
	public Optional<Questionnaire> findByID(int id) {
		return QueRepo.findById(id);
		
	}
	
	public List<String> findQuestionnaire() {
		return QueRepo.findQuestionnaire();	
	}
	
	public Optional<Questionnaire> findForDeatils(String title) {
		return QueRepo.findForDetails(title);
	}

}
