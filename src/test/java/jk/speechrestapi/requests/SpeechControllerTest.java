package jk.speechrestapi.requests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import jk.speechrestapi.controllers.SpeechController;
import jk.speechrestapi.model.Author;
import jk.speechrestapi.model.Speech;
import jk.speechrestapi.model.SpeechDTO;
import jk.speechrestapi.repository.AuthorRepository;
import jk.speechrestapi.repository.SpeechRepository;

@RunWith(MockitoJUnitRunner.class)
public class SpeechControllerTest {
	
	private SpeechController speechController;
	
	@Mock
	private SpeechRepository mockedSpeechRepository;
	
	@Mock
	private AuthorRepository mockedAuthorRepository;
		
	private SpeechDTO speechDTO;
	
	private SpeechDTO invalidSpeechDTO;
	
	private Speech speech;
	
	private Author author;
	
	@Before
	public void setup() {
		speechController = new SpeechController(mockedSpeechRepository, mockedAuthorRepository);
	}

	@Test
	public void testAddSpeechIsValid() {

		// Create a valid speech DTO
		speechDTO = new SpeechDTO();
		speechDTO.setSpeechId(1L);
		speechDTO.setMetadata("meta test dto");
		speechDTO.setSubject("subject test dto");
		speechDTO.setText("text test dto");
		speechDTO.setKeywords("kw test dto");
		speechDTO.setDate(LocalDate.now());
		speechDTO.setAuthorId(1L);

		// Create a valid speech 
		speech = new Speech();
		speech.setSpeechId(1L);
		speech.setMetadata("metatest");
		speech.setSubject("subject test");
		speech.setText("text test");
		speech.setKeywords("kw test");
		speech.setDate(LocalDate.now());	
		
		// Create a valid author
		author = new Author();
		author.setAuthorId(1L);
		author.setEmail("test@test.com");
		author.setName("George");
		speech.setAuthor(author);
		
		when(mockedSpeechRepository.save(any(Speech.class))).thenReturn(speech);
		when(mockedAuthorRepository.findById(anyLong())).thenReturn(Optional.of(author));

		ResponseEntity<Speech> speechResponse = speechController.addSpeech(speechDTO);
		
		Speech validSpeech = (Speech) speechResponse.getBody();
		
		assertThat(validSpeech.equals(speech));
	}
	
	
	@Test(expected = ResponseStatusException.class)
	public void testAddSpeechIsInvalid() {
		
		// Create an invalid speech(no name) and expects an exception
		invalidSpeechDTO = new SpeechDTO();
		invalidSpeechDTO.setSpeechId(1L);
		invalidSpeechDTO.setText("");

		ResponseEntity<Speech> invalidSpeechResponse = speechController.addSpeech(invalidSpeechDTO);
	}
	
	
	@Test
	public void testDeleteSpeechIsValid() {
		
		when(mockedSpeechRepository.existsById(anyLong())).thenReturn(true);
		doNothing().when(mockedSpeechRepository).deleteById(anyLong());

		ResponseEntity<String> speechResponse = speechController.deleteSpeech(1L);
		
		assertThat(speechResponse.getBody().equals("Speech successfully deleted"));
	}
	
	@Test(expected = ResponseStatusException.class)
	public void testDeleteSpeechIsInvalid() {
		
		ResponseEntity<String> speechResponse = speechController.deleteSpeech(null);
		
	}
	
	

}
