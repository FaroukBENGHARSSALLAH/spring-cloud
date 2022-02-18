package com.farouk.bengarssallah.cloud.spring.aggregate;

import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.Getter;

public abstract class BaseAggregate<T> {
	
	@AggregateIdentifier
	@Getter protected T reference;
	
}
