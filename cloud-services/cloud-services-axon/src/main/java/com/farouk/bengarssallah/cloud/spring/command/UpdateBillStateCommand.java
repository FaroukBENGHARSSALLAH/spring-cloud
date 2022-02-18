package com.farouk.bengarssallah.cloud.spring.command;

import java.util.UUID;

import lombok.Getter;

public class UpdateBillStateCommand extends BaseCommand<UUID> {
	
	@Getter private String state;
	
	public UpdateBillStateCommand(UUID reference, String state) {
		super(reference);
		this.state = state;
	}
	
}
