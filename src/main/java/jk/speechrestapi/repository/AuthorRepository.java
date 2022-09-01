package jk.speechrestapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jk.speechrestapi.model.Author;
import jk.speechrestapi.model.Speech;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long>{
	

}
