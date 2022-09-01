package jk.speechrestapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import jk.speechrestapi.model.Author;
import jk.speechrestapi.model.AuthorDTO;
import jk.speechrestapi.model.Speech;
import jk.speechrestapi.model.SpeechDTO;

@Mapper(componentModel = "Spring")
public abstract class AuthorMapper {
	
	public static final AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);
	
	public abstract Author AuthorDTOtoAuthor(AuthorDTO authorDTO);	
	

}
