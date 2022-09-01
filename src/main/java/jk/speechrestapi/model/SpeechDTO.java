package jk.speechrestapi.model;

import java.time.LocalDate;
import javax.persistence.Column;
import org.springframework.format.annotation.DateTimeFormat;

public class SpeechDTO {

	private Long speechId;

	@Column(length = 250)
	private String metadata;

	@Column(length = 250)
	private String subject;

	@Column(length = 20000)
	private String text;

	@Column(length = 250)
	private String keywords;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;

	private Long authorId;
	
	private Author author;

	public Long getSpeechId() {
		return speechId;
	}

	public void setSpeechId(Long speechId) {
		this.speechId = speechId;
	}

	public String getMetadata() {
		return metadata;
	}

	public void setMetadata(String metadata) {
		this.metadata = metadata;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

}
