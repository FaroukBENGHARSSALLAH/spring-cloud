package com.farouk.bengarssallah.cloud.spring.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.farouk.bengarssallah.cloud.spring.domain.Client;
import com.farouk.bengarssallah.cloud.spring.dto.ClientRequestDTO;
import com.farouk.bengarssallah.cloud.spring.exception.ClientNotFoundException;
import com.farouk.bengarssallah.cloud.spring.repository.ClientRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class ClientServiceImpl  implements ClientService {
	
	private ClientRepository clientRepository;
	
	public Client addClient(Client client) {
		log.info("Client Created with Reference : " + client.getReference());
		return clientRepository.save(client);
	}
	
	
	public Client putClient(ClientRequestDTO clientDTO) {
		log.info("Client Updated with Reference : " + client.getReference());
		Client persistedClient = clientRepository.getOne(client.getReference());
		if(persistedClient == null) throw new ClientNotFoundException("Client Not in the system");
		persistedClient.setFullName(client.getFullName());
		persistedClient.setEmail(client.getEmail());
		return clientRepository.save(persistedClient);
	}
	
	public void putClientUnpaidBills(UUID reference) {
		log.info("Client Unpaid Bills Updated with Reference : " + reference);
		Client persistedClient = clientRepository.getOne(reference);
		if(persistedClient == null) throw new ClientNotFoundException("Client Not in the system");
		if(persistedClient.getUnpaidBills() <= 1) persistedClient.setUnpaidBills(0);
		else persistedClient.setUnpaidBills(persistedClient.getUnpaidBills() - 1);
		persistedClient.setEmail(persistedClient.getEmail());
        clientRepository.save(persistedClient);
	}
	
	
	public void deleteClient(UUID reference) {
		log.info("Client Deleted with Reference : " + reference);
		Client client = clientRepository.getOne(reference);
		if(client == null) throw new ClientNotFoundException("Client Not in the system");
		clientRepository.deleteById(reference);
	}
	
	public List<Client> getAllClients() {
		log.info("GetAllClientsQuery received ");
		return clientRepository.findAll();
	}
	
	public Client getClientByReference(UUID reference) {
		log.info("GetClientQuery received with Reference : " + reference);
		Client client = clientRepository.getOne(reference);
		if(client == null) throw new ClientNotFoundException("Client Not in the system");
		return client;
	}

}
