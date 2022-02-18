package com.farouk.bengarssallah.cloud.spring.event;

import java.util.UUID;

import lombok.Getter;

public class BillStateChangedEvent extends BaseEvent<UUID> {

	@Getter private String state;

	public BillStateChangedEvent(UUID reference, String state) {
		super(reference);
		this.state = state;
	}

}
