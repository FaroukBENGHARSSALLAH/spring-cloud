package com.farouk.bengarssallah.cloud.spring.event;

import java.util.UUID;

import lombok.Getter;

public class ClientUpdatedEvent extends BaseEvent<UUID> {

	@Getter private String fullName;
	@Getter private String email;

	public ClientUpdatedEvent(UUID reference, String fullName, String email) {
		super(reference);
		this.fullName = fullName;
		this.email = email;
	}
	
	

}
