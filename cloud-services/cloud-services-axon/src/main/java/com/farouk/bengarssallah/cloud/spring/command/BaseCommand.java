package com.farouk.bengarssallah.cloud.spring.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Getter;

public abstract class BaseCommand<T> {
	
	@TargetAggregateIdentifier
	@Getter private T reference;

	public BaseCommand(T reference) {
		this.reference = reference;
	}
	
}
