package com.farouk.bengarssallah.cloud.spring.event;

import lombok.Getter;

public abstract class BaseEvent<T> {
	
	@Getter private T reference;

	public BaseEvent(T reference) {
		this.reference = reference;
	}

}
