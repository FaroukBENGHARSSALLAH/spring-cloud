package com.farouk.bengarssallah.cloud.spring.axon.test;

import java.util.Date;
import java.util.UUID;

import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

import com.farouk.bengarssallah.cloud.spring.aggregate.ClientAggregate;
import com.farouk.bengarssallah.cloud.spring.command.CreateClientCommand;
import com.farouk.bengarssallah.cloud.spring.command.DeleteClientCommand;
import com.farouk.bengarssallah.cloud.spring.command.UpdateClientCommand;
import com.farouk.bengarssallah.cloud.spring.event.ClientCreatedEvent;
import com.farouk.bengarssallah.cloud.spring.event.ClientDeletedEvent;
import com.farouk.bengarssallah.cloud.spring.event.ClientUpdatedEvent;

public class ClientAggregateTest {
	
	private static final String fullName = "client";
	private static final String email = "email";
	private static final Date creationDate = new Date();


    private FixtureConfiguration<ClientAggregate> fixture;
    private UUID reference;

    @Before
    public void setUp() {
        fixture = new AggregateTestFixture<>(ClientAggregate.class);
        reference = UUID.randomUUID();
    }
    
    @Test
    public void should_dispatch_clientcreated_event_when_createclient_command() {
        fixture.givenNoPriorActivity()
                .when(new CreateClientCommand(
                		reference, 
                		fullName, 
                		email, 
                		creationDate)
                )
                .expectEvents(new ClientCreatedEvent(
                		reference, 
                		fullName, 
                		email, 
                		creationDate)
                );
    }
    
    @Test
    public void should_dispatch_clientupdated_event_when_updateclient_command() {
        fixture.given(new ClientCreatedEvent(
        		        reference, 
        		        fullName, 
        		        email, 
        		        creationDate))
                .when(new UpdateClientCommand(
                		reference, 
                		fullName, 
                		email)
                )
                .expectEvents(new ClientUpdatedEvent(
                		reference, 
                		fullName, 
                		email)
                );
    }
    
    @Test
    public void should_dispatch_clientdeleted_event_when_deleteclient_command() {
        fixture.given(new ClientCreatedEvent(
				        reference, 
				        fullName, 
				        email, 
				        creationDate))
                .when(new DeleteClientCommand(
                		reference)
                )
                .expectEvents(new ClientDeletedEvent(
                		reference)
                );
    }

}
