package com.farouk.bengarssallah.cloud.spring.event;

import java.util.Date;
import java.util.UUID;

import lombok.Getter;

public class ClientCreatedEvent extends BaseEvent<UUID> {

	@Getter private String fullName;
	@Getter private String email;
	@Getter private Date creationDate;

	public ClientCreatedEvent(UUID reference, String fullName, String email, Date creationDate) {
		super(reference);
		this.fullName = fullName;
		this.email = email;
		this.creationDate = creationDate;
	}
	
	

}
