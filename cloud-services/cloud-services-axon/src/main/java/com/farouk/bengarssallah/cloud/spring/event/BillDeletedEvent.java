package com.farouk.bengarssallah.cloud.spring.event;

import java.util.UUID;

public class BillDeletedEvent extends BaseEvent<UUID> {

	
	public BillDeletedEvent(UUID reference) {
		super(reference);
	}
	
	

}
