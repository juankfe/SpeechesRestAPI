package jk.speechrestapi.model;

public class Utilities {
	
	public boolean validateAuthorDTO(AuthorDTO authorDTO) {
		
		boolean isValid = true;
		
		if(authorDTO.getName() == null || authorDTO.getName().isBlank()) {
			isValid=false;
		}else if(authorDTO.getEmail() == null || authorDTO.getEmail().isBlank()) {
			isValid=false;
		}
		
		return isValid;
	}
	
	
	public boolean validateSpeechDTO(SpeechDTO speechDTO) {
		
		boolean isValid = true;
		
		if(speechDTO.getMetadata() == null || speechDTO.getMetadata().isBlank()) {
			isValid=false;
		}else if(speechDTO.getSubject() == null || speechDTO.getSubject().isBlank()){
			isValid=false;
		}else if(speechDTO.getText() == null || speechDTO.getText().isBlank()){
			isValid=false;
		}else if(speechDTO.getKeywords() == null || speechDTO.getKeywords().isBlank()){
			isValid=false;
		}else if(speechDTO.getDate() == null){
			isValid=false;
		}else if(speechDTO.getAuthorId() == null){
			isValid=false;
		}		
		
		return isValid;
	}

}
