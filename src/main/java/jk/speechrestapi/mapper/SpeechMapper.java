package jk.speechrestapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import jk.speechrestapi.model.Speech;
import jk.speechrestapi.model.SpeechDTO;

@Mapper(componentModel = "Spring")
public abstract class SpeechMapper {
	
	public static final SpeechMapper INSTANCE = Mappers.getMapper(SpeechMapper.class);
		
	public abstract Speech SpeechDTOtoSpeech(SpeechDTO speechDTO);

}
