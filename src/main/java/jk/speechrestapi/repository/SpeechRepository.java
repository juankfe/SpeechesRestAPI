package jk.speechrestapi.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jk.speechrestapi.model.Speech;

@Repository
public interface SpeechRepository extends JpaRepository<Speech, Long>{
	
	@Query(value = "select s from Speech s where s.author.authorId = ?1")
	List<Speech> searchSpeechesByAuthorId(Long authorId);
	
	@Query(value = "select s from Speech s where lower(s.subject) like %?1%")
	List<Speech> searchSpeechesBySubject(String subject);
	
	@Query(value = "select s from Speech s where lower(s.text) like %?1%")
	List<Speech> searchSpeechesByText(String text);
	
	@Query(value = "select s from Speech s where s.date between :startDate and :endDate")	
	List<Speech> searchSpeechesByDateRange(@Param("startDate")LocalDate startDate,@Param("endDate")LocalDate endDate);
}
