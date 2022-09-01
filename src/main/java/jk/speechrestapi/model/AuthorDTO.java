package jk.speechrestapi.model;

import javax.persistence.Column;


public class AuthorDTO {
	
	private Long authorId;
	
	@Column(length=250)
	private String name;
	
	@Column(length=250)
	private String email;

	public Long getAuthorId() {
		return authorId;
	}

	public void setAuthorId(Long authorId) {
		this.authorId = authorId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
