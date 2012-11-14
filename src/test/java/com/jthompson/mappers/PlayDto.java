package com.jthompson.mappers;

import javax.persistence.Column;

import lombok.Data;

@Data
public class PlayDto 
{

	private String id;
	
	private String email;
	
	@Column(name="FIRST_NAME")
	private String firstName;
	
	private String lastName;
	
}
