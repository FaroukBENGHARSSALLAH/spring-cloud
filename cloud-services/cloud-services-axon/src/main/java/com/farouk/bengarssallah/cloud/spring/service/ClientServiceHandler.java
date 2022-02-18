package com.farouk.bengarssallah.cloud.spring.service;

import java.util.List;

import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

import com.farouk.bengarssallah.cloud.spring.domain.Client;
import com.farouk.bengarssallah.cloud.spring.event.ClientCreatedEvent;
import com.farouk.bengarssallah.cloud.spring.event.ClientDeletedEvent;
import com.farouk.bengarssallah.cloud.spring.event.ClientUpdatedEvent;
import com.farouk.bengarssallah.cloud.spring.exception.ClientNotFoundException;
import com.farouk.bengarssallah.cloud.spring.query.GetAllClientsQuery;
import com.farouk.bengarssallah.cloud.spring.query.GetClientQuery;
import com.farouk.bengarssallah.cloud.spring.repository.ClientRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class ClientServiceHandler {
	
	private ClientRepository clientRepository;
	
	@EventHandler
	public void on(ClientCreatedEvent event) {
		log.info("Client Created with Reference : " + event.getReference());
		clientRepository.save(new Client(
				event.getReference(), 
				event.getFullName(),
				event.getEmail(),
				event.getCreationDate()
			   )
			);
	}
	
	
	@EventHandler
	public void on(ClientUpdatedEvent event) {
		log.info("Client Updated with Reference : " + event.getReference());
		Client client = clientRepository.getOne(event.getReference());
		if(client == null) throw new ClientNotFoundException("Client Not in the system");
		client.setFullName(event.getFullName());
		client.setEmail(event.getEmail());
		clientRepository.save(client);
	}
	
	
	@EventHandler
	public void on(ClientDeletedEvent event) {
		log.info("Client Deleted with Reference : " + event.getReference());
		Client client = clientRepository.getOne(event.getReference());
		if(client == null) throw new ClientNotFoundException("Client Not in the system");
		clientRepository.deleteById(event.getReference());
	}
	
	@QueryHandler
	public List<Client> on(GetAllClientsQuery query) {
		log.info("GetAllClientsQuery received ");
		return clientRepository.findAll();
	}
	
	@QueryHandler
	public Client on(GetClientQuery query) {
		log.info("GetClientQuery received with Reference : " + query.getReference());
		Client client = clientRepository.getOne(query.getReference());
		if(client == null) throw new ClientNotFoundException("Client Not in the system");
		return client;
	}

}
