package com.farouk.bengarssallah.cloud.spring.command;

import java.util.Date;
import java.util.UUID;

import lombok.Getter;


public class GenerateBillCommand extends BaseCommand<UUID> {
	
	@Getter private String type;
	@Getter private String period;
	@Getter private String state;
	@Getter private double amount;
	@Getter private UUID clientReference;
	@Getter private Date generationDate;
	@Getter private Date deadline;
	
	public GenerateBillCommand(UUID reference, String type, String period, String state, double amount, UUID clientReference, 
			Date generationDate, Date deadline) {
		super(reference);
		this.type = type;
		this.period = period;
		this.state = state;
		this.amount = amount;
		this.clientReference = clientReference;
		this.generationDate = generationDate;
		this.deadline = deadline;
	}
	
}
