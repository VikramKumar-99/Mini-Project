package com.demo.project.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.demo.project.models.Questionnaire;

@Repository
public interface QuestionnaireRepository extends JpaRepository<Questionnaire, Integer> {
	Optional<Questionnaire> findById(int id);
	
	@Query(value = "SELECT title FROM project.questionnaires", nativeQuery = true)
	 List<String> findQuestionnaire();
	
	@Query(value = "SELECT * FROM project.questionnaires where title = ?1", nativeQuery = true )
	Optional<Questionnaire> findForDetails(String title);

}
