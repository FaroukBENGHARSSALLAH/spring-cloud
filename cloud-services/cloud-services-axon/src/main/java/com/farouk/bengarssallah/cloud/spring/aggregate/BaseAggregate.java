package com.farouk.bengarssallah.cloud.spring.aggregate;

import java.util.UUID;

import org.axonframework.modelling.command.AggregateIdentifier;

import lombok.Getter;


public abstract class BaseAggregate {
	
	@AggregateIdentifier
	@Getter protected UUID reference;
	
}
