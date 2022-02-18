package com.farouk.bengarssallah.cloud.spring.aggregate;

import java.util.Date;
import java.util.UUID;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import com.farouk.bengarssallah.cloud.spring.command.CreateClientCommand;
import com.farouk.bengarssallah.cloud.spring.command.DeleteClientCommand;
import com.farouk.bengarssallah.cloud.spring.command.UpdateClientCommand;
import com.farouk.bengarssallah.cloud.spring.event.ClientCreatedEvent;
import com.farouk.bengarssallah.cloud.spring.event.ClientDeletedEvent;
import com.farouk.bengarssallah.cloud.spring.event.ClientUpdatedEvent;

import lombok.Getter;

@Aggregate
@Getter
public class ClientAggregate extends BaseAggregate<UUID> {
	
	private String fullName;
	private String email;
	private Date creationDate;
	
	public ClientAggregate() {
		
	}
	
	@CommandHandler
	public ClientAggregate(CreateClientCommand command) {
		  AggregateLifecycle.apply(new ClientCreatedEvent(
				  command.getReference(), 
				  command.getFullName(), 
				  command.getEmail(), 
				  command.getCreationDate()
				  )
			  );
	 }
	
	@EventSourcingHandler
	public void on(ClientCreatedEvent event) {
		  this.reference = event.getReference();
		  this.fullName = event.getFullName();
		  this.email = event.getEmail();
		  this.creationDate = event.getCreationDate();
	}
	
	@CommandHandler
	public void handle(UpdateClientCommand command) {
		  AggregateLifecycle.apply(new ClientUpdatedEvent(
				  command.getReference(), 
				  command.getFullName(), 
				  command.getEmail()
				  )
			  );
	 }
	
	@EventSourcingHandler
	public void on(ClientUpdatedEvent event) {
		  this.reference = event.getReference();
		  this.fullName = event.getFullName();
		  this.email = event.getEmail();
	}
	
	@CommandHandler
	public void handle(DeleteClientCommand command) {
		  AggregateLifecycle.apply(new ClientDeletedEvent(
				  command.getReference()
				  )
			  );
	 }
	
	@EventSourcingHandler
	public void on(ClientDeletedEvent event) {
		  this.reference = event.getReference();
	}

}
