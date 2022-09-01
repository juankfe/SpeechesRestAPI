package jk.speechrestapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jk.speechrestapi.mapper.AuthorMapper;
import jk.speechrestapi.model.Author;
import jk.speechrestapi.model.AuthorDTO;
import jk.speechrestapi.model.Utilities;
import jk.speechrestapi.repository.AuthorRepository;

@RestController
public class AuthorController {

	@Autowired
	private AuthorRepository authorRepository;

	public AuthorController(AuthorRepository authorRepository) {
		this.authorRepository = authorRepository;
	}

	// Endpoint to add an author
	@PostMapping(value = "/addauthor", produces = "application/json")
	public ResponseEntity<Author> addAuthor(@RequestBody AuthorDTO authorDTO) {

		Utilities util = new Utilities();
		boolean isValidAuthorDTO = util.validateAuthorDTO(authorDTO);
		
		if (isValidAuthorDTO == false) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid input");
		} else {
			try {
				Author author = AuthorMapper.INSTANCE.AuthorDTOtoAuthor(authorDTO);
				Author addedAuthor = authorRepository.save(author);
				return new ResponseEntity<Author>(addedAuthor, HttpStatus.CREATED);
				
			} catch (Exception ex) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
			}
		}
	}

}
