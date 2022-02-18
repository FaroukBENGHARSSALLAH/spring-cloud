package com.farouk.bengarssallah.cloud.spring.command;

import java.util.UUID;

import lombok.Getter;

public class UpdateClientCommand extends BaseCommand<UUID> {
	
	@Getter private String fullName;
	@Getter private String email;
	
	public UpdateClientCommand(UUID reference, String fullName, String email) {
		super(reference);
		this.fullName = fullName;
		this.email = email;
	}
	
}
