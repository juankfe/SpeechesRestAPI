package jk.speechrestapi.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jk.speechrestapi.mapper.SpeechMapper;
import jk.speechrestapi.model.Author;
import jk.speechrestapi.model.Speech;
import jk.speechrestapi.model.SpeechDTO;
import jk.speechrestapi.model.Utilities;
import jk.speechrestapi.repository.AuthorRepository;
import jk.speechrestapi.repository.SpeechRepository;

@RestController
public class SpeechController {

	@Autowired
	private SpeechRepository speechRepository;

	@Autowired
	private AuthorRepository authorRepository;
	
	public SpeechController(SpeechRepository speechRepository, AuthorRepository authorRepository) {
		this.speechRepository = speechRepository;
		this.authorRepository = authorRepository;
	}

	// Endpoint to add a speech
	@PostMapping(value = "/addspeech", produces = "application/json")
	public ResponseEntity<Speech> addSpeech(@RequestBody SpeechDTO speechDTO) {
		
		Utilities util = new Utilities();			
		boolean isValidSpeechDTO = util.validateSpeechDTO(speechDTO);
		
		if(isValidSpeechDTO == false) {			
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid input. All fields are required.");
		}else {
			
			Author author = new Author();

			try {
				author = authorRepository.findById(speechDTO.getAuthorId()).get();
				
			} catch (Exception ex) {
				throw new ResponseStatusException(HttpStatus.OK, "Author not found.");
			}
			
			
			try {
				speechDTO.setAuthor(author);
				Speech speech = SpeechMapper.INSTANCE.SpeechDTOtoSpeech(speechDTO);
				Speech addedSpeech = speechRepository.save(speech);
				return new ResponseEntity<Speech>(addedSpeech, HttpStatus.CREATED);

			} catch (Exception ex) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
			}			
		}
	}

	// Endpoint to delete a speech
	@DeleteMapping(value = "/deletespeech", produces = "application/json")
	public ResponseEntity<String> deleteSpeech(@RequestParam(value = "speechid") Long speechId) {
		
		if(speechId == null) {			
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid speechid.");
		}
		
		boolean speechExists = false;
		boolean speechDeleted = false;
		
		try {
			speechExists = speechRepository.existsById(speechId);
			
			if(speechExists == true) {
				speechRepository.deleteById(speechId);
				speechDeleted = true;
			}
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
		}
		
		if(speechDeleted == true) {
			return new ResponseEntity<String>("Speech successfully deleted", HttpStatus.OK);
		}else {
			throw new ResponseStatusException(HttpStatus.OK, "Speech does not exist.");
		}
		
	}

	// Endpoint to edit speech
	@PutMapping(value = "/editspeech", produces = "application/json")
	@ResponseBody
	public ResponseEntity<Speech> editSpeech(@RequestBody SpeechDTO speechDTO) {
		
		Utilities util = new Utilities();			
		boolean isValidSpeechDTO = util.validateSpeechDTO(speechDTO);
		
		if(isValidSpeechDTO == false || speechDTO.getSpeechId() == null) {			
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid input. All fields are required.");
		}else {
			
			boolean speechExists;
			
			try {				
				speechExists = speechRepository.existsById(speechDTO.getSpeechId());
			} catch (Exception ex) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
			}	
			
			
			if (speechExists == true) {
				
				Author author = new Author();

				try {
					author = authorRepository.findById(speechDTO.getAuthorId()).get();
				} catch (Exception ex) {						
					throw new ResponseStatusException(HttpStatus.OK, "Author not found.");
				}
				
				try {
					speechDTO.setAuthor(author);
					Speech speech = SpeechMapper.INSTANCE.SpeechDTOtoSpeech(speechDTO);
					Speech updatedSpeech = speechRepository.save(speech);
					return new ResponseEntity<Speech>(updatedSpeech, HttpStatus.CREATED);	
					
				} catch (Exception ex) {						
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
				}
				
			} else {					
				throw new ResponseStatusException(HttpStatus.OK, "Speech not found");
			}
			
		}
		
	}

	// Endpoint to search speeches by author
	@GetMapping(value = "/searchspeechbyauthor", produces = "application/json")
	@ResponseBody
	public ResponseEntity<List<Speech>> searchSpeechByAuthor(@RequestParam(value = "authorid") Long authorId) {		
		
		if(authorId == null) {			
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid authorid.");
		}
		
		List<Speech> speeches;
		
		try {
			speeches = speechRepository.searchSpeechesByAuthorId(authorId);
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
		}
		
		if (speeches.isEmpty()) {				
			throw new ResponseStatusException(HttpStatus.OK, "No speeches were found.");
		}else {
			return new ResponseEntity<List<Speech>>(speeches, HttpStatus.OK);
		}
		
	}

	// Endpoint to search speeches by subject area
	@GetMapping(value = "/searchspeechbysubject", produces = "application/json")
	@ResponseBody
	public ResponseEntity<List<Speech>> searchSpeechBySubject(@RequestParam(value = "subject") String subject) {
		
		if(subject.isBlank()) {			
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid subject.");
		}
		
		List<Speech> speeches;
		
		try {
			speeches = speechRepository.searchSpeechesBySubject(subject.trim().toLowerCase());			
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
		}
		
		if (speeches.isEmpty()) {				
			throw new ResponseStatusException(HttpStatus.OK, "No speeches were found.");
		}else {
			return new ResponseEntity<List<Speech>>(speeches, HttpStatus.OK);
		}
		
	}

	// Endpoint to search speeches by text snippet
	@GetMapping(value = "/searchspeechbytext", produces = "application/json")
	@ResponseBody
	public ResponseEntity<List<Speech>> searchSpeechByText(@RequestParam(value = "text") String text) {
		
		if(text.isBlank()) {			
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid text snippet.");
		}		
		
		List<Speech> speeches;
		
		try {
			speeches = speechRepository.searchSpeechesByText(text.trim().toLowerCase());
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
		}
		
		if (speeches.isEmpty()) {				
			throw new ResponseStatusException(HttpStatus.OK, "No speeches were found.");
		}else {
			return new ResponseEntity<List<Speech>>(speeches, HttpStatus.OK);
		}
		
	}

	// Endpoint to search speeches by date range
	@GetMapping(value = "/searchspeechbydaterange", produces = "application/json")
	@ResponseBody
	public ResponseEntity<List<Speech>> searchSpeechByDateRange(
			@RequestParam(value = "startdate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
			@RequestParam(value = "enddate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
		
		if(startDate == null || endDate == null) {			
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid startdate or enddate.");
		}			
		
		List<Speech> speeches;
		
		try {			
			speeches = speechRepository.searchSpeechesByDateRange(startDate, endDate);
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
		}

		if (speeches.isEmpty()) {				
			throw new ResponseStatusException(HttpStatus.OK, "No speeches were found.");
		}else {
			return new ResponseEntity<List<Speech>>(speeches, HttpStatus.OK);
		}
	}

}
