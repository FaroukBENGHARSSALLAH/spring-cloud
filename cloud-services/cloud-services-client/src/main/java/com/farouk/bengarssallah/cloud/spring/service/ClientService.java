package com.farouk.bengarssallah.cloud.spring.service;

import java.util.List;
import java.util.UUID;

import com.farouk.bengarssallah.cloud.spring.domain.Client;

public interface ClientService {
	
	
	public Client addClient(Client client);
	
	public Client putClient(UUID reference, String fullName, String email);
	
	public void putClientUnpaidBills(UUID reference);

	public void deleteClient(UUID reference);
	
	public List<Client> getAllClients();
	
	public Client getClientByReference(UUID reference);


}
