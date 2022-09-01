package jk.speechrestapi.requests;

import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import jk.speechrestapi.controllers.AuthorController;
import jk.speechrestapi.model.Author;
import jk.speechrestapi.model.AuthorDTO;
import jk.speechrestapi.repository.AuthorRepository;

@RunWith(MockitoJUnitRunner.class)
public class AuthorControllerTest {
	
	private AuthorController authorController;
	
	@Mock
	private AuthorRepository mockedAuthorRepository;
		
	private AuthorDTO authorDTO;
	
	private AuthorDTO invalidAuthorDTO;
	
	private Author author;
	
	@Before
	public void setup() {
		authorController = new AuthorController(mockedAuthorRepository);
	}
	
	@Test
	public void testAddAuthorIsValid() {

		// Create a valid author DTO
		authorDTO = new AuthorDTO();
		authorDTO.setAuthorId(1L);
		authorDTO.setEmail("test@test.com");
		authorDTO.setName("George");
	
		// Create a valid author
		author = new Author();
		author.setAuthorId(1L);
		author.setEmail("test@test.com");
		author.setName("George");
		
		when(mockedAuthorRepository.save(any(Author.class))).thenReturn(author);
		
		ResponseEntity<Author> authorResponse = authorController.addAuthor(authorDTO);
		
		Author validAuthor = (Author) authorResponse.getBody();
		
		assertThat(validAuthor.equals(author));
	}
	
	@Test(expected = ResponseStatusException.class)
	public void testAddAuthorIsInvalid() {
		
		// Create an invalid author(no name) and expects an exception
		invalidAuthorDTO = new AuthorDTO();
		invalidAuthorDTO.setAuthorId(1L);
		invalidAuthorDTO.setName("");
		
		ResponseEntity<Author> invalidAuthorResponse = authorController.addAuthor(invalidAuthorDTO);
	}
	
}

