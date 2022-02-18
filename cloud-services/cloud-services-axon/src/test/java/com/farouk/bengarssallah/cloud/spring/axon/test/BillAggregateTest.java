package com.farouk.bengarssallah.cloud.spring.axon.test;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

import com.farouk.bengarssallah.cloud.spring.aggregate.BillAggregate;
import com.farouk.bengarssallah.cloud.spring.command.DeleteBillCommand;
import com.farouk.bengarssallah.cloud.spring.command.GenerateBillCommand;
import com.farouk.bengarssallah.cloud.spring.command.UpdateBillStateCommand;
import com.farouk.bengarssallah.cloud.spring.event.BillDeletedEvent;
import com.farouk.bengarssallah.cloud.spring.event.BillGeneratedEvent;
import com.farouk.bengarssallah.cloud.spring.event.BillStateChangedEvent;

public class BillAggregateTest {
	
	   private static final String state = "generated";
	   private static final String type = "annual";
	   private static final String period = "2021-2022";
	   private static final Date generationDate = new Date();
	   private static final UUID clientReference = UUID.randomUUID();
	   private static final double amount = new Random().nextDouble();
	   private FixtureConfiguration<BillAggregate> fixture;
	   private UUID reference;
	   
	   @Before
	   public void setUp() {
	      this.fixture = new AggregateTestFixture(BillAggregate.class);
	      this.reference = UUID.randomUUID();
	   }
	   
	   @Test
	   public void should_dispatch_billcreated_event_when_createbill_command() {
	      this.fixture.givenNoPriorActivity()
	           .when(new GenerateBillCommand(
	        		   reference, 
	        		   type, 
	        		   period, 
	        		   state, 
	        		   amount,
	        		   clientReference, 
	        		   generationDate, 
	        		   generationDate)
	           )
               .expectEvents(new BillGeneratedEvent(
            		   reference, 
            		   type, 
            		   period, 
            		   state, 
            		   amount,
            		   clientReference, 
            		   generationDate, 
            		   generationDate) 
	           );
	   }
	   
	   @Test
	   public void should_dispatch_billstateupdated_event_when_updatebillstate_command() {
	      this.fixture.given(new BillGeneratedEvent(
		       		   reference, 
		       		   type, 
		       		   period, 
		       		   state, 
		       		   amount,
		       		   clientReference, 
		       		   generationDate, 
		       		   generationDate))
	           .when(new UpdateBillStateCommand(
	        		   reference, 
	        		   state) 
	           )
               .expectEvents(new BillStateChangedEvent(
            		   reference, 
            		   state)
	           );
	   }
	   
	   @Test
	    public void should_dispatch_billdeleted_event_when_deletebill_command() {
	        fixture.given(new BillGeneratedEvent(
		       		   reference, 
		       		   type, 
		       		   period, 
		       		   state, 
		       		   amount,
		       		   clientReference, 
		       		   generationDate, 
		       		   generationDate))
	                .when(new DeleteBillCommand(
	                   reference)
	                )
	                .expectEvents(new BillDeletedEvent(
	                	reference)
	                );
	    }
	   

}
