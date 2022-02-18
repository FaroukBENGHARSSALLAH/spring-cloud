package com.farouk.bengarssallah.cloud.spring.event;

import java.util.UUID;

public class ClientDeletedEvent extends BaseEvent<UUID> {

	
	public ClientDeletedEvent(UUID reference) {
		super(reference);
	}
	
	

}
