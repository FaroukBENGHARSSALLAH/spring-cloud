package com.farouk.bengarssallah.cloud.spring.aggregate;

import java.util.Date;
import java.util.UUID;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import com.farouk.bengarssallah.cloud.spring.command.DeleteBillCommand;
import com.farouk.bengarssallah.cloud.spring.command.GenerateBillCommand;
import com.farouk.bengarssallah.cloud.spring.command.UpdateBillStateCommand;
import com.farouk.bengarssallah.cloud.spring.event.BillDeletedEvent;
import com.farouk.bengarssallah.cloud.spring.event.BillGeneratedEvent;
import com.farouk.bengarssallah.cloud.spring.event.BillStateChangedEvent;

import lombok.Getter;

@Aggregate
@Getter
public class BillAggregate extends BaseAggregate {
	
	private String type;
	private String period;
	private String state;
	private double amount;
	private UUID clientReference;
	private Date generationDate;
	private Date deadline;
	
	public BillAggregate() {
		
	}
	
	@CommandHandler
	public BillAggregate(GenerateBillCommand command) {
		  AggregateLifecycle.apply(new BillGeneratedEvent(
				  command.getReference(), 
				  command.getType(), 
				  command.getPeriod(), 
				  command.getState(), 
				  command.getAmount(),
				  command.getClientReference(),
				  command.getGenerationDate(), 
				  command.getDeadline()
				  )
			  );
	 }
	
	@EventSourcingHandler
	public void on(BillGeneratedEvent event) {
		  this.reference = event.getReference();
		  this.type = event.getType(); 
		  this.period = event.getPeriod();
		  this.state = event.getState();
		  this.amount = event.getAmount();
		  this.clientReference = event.getClientReference();
		  this.generationDate = event.getGenerationDate();
		  this.deadline = event.getDeadline();
	}
	
	@CommandHandler
	public void handle(UpdateBillStateCommand command) {
		  AggregateLifecycle.apply(new BillStateChangedEvent(
				  command.getReference(), 
				  command.getState()
				  )
			  );
	 }
	
	@EventSourcingHandler
	public void on(BillStateChangedEvent event) {
		  this.reference = event.getReference();
		  this.state = event.getState();
	}
	
	@CommandHandler
	public void handle(DeleteBillCommand command) {
		  AggregateLifecycle.apply(new BillDeletedEvent(
				  command.getReference()
				  )
			  );
	 }
	
	@EventSourcingHandler
	public void on(BillDeletedEvent event) {
		  this.reference = event.getReference();
	}
	
	

}
