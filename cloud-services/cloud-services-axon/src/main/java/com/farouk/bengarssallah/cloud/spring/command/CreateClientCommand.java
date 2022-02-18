package com.farouk.bengarssallah.cloud.spring.command;

import java.util.Date;
import java.util.UUID;

import lombok.Getter;


public class CreateClientCommand extends BaseCommand<UUID> {
	
	@Getter private String fullName;
	@Getter private String email;
	@Getter private Date creationDate;
	
	public CreateClientCommand(UUID reference, String fullName, String email, Date creationDate) {
		super(reference);
		this.fullName = fullName;
		this.email = email;
		this.creationDate = creationDate;
	}
	
}
