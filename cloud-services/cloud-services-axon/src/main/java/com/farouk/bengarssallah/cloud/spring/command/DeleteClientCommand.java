package com.farouk.bengarssallah.cloud.spring.command;

import java.util.UUID;

public class DeleteClientCommand extends BaseCommand<UUID> {
	
	public DeleteClientCommand(UUID reference) {
		super(reference);
	}
	
}
