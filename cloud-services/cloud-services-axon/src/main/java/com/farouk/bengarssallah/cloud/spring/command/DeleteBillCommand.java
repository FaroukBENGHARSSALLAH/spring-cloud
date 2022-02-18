package com.farouk.bengarssallah.cloud.spring.command;

import java.util.UUID;

public class DeleteBillCommand extends BaseCommand<UUID> {
	
	public DeleteBillCommand(UUID reference) {
		super(reference);
	}
	
}
