package com.farouk.bengarssallah.cloud.spring.service;

import java.util.List;

import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

import com.farouk.bengarssallah.cloud.spring.domain.Bill;
import com.farouk.bengarssallah.cloud.spring.domain.Client;
import com.farouk.bengarssallah.cloud.spring.event.BillDeletedEvent;
import com.farouk.bengarssallah.cloud.spring.event.BillGeneratedEvent;
import com.farouk.bengarssallah.cloud.spring.event.BillStateChangedEvent;
import com.farouk.bengarssallah.cloud.spring.exception.BillNotFoundException;
import com.farouk.bengarssallah.cloud.spring.exception.ClientNotFoundException;
import com.farouk.bengarssallah.cloud.spring.query.GetAllBillsQuery;
import com.farouk.bengarssallah.cloud.spring.query.GetBillQuery;
import com.farouk.bengarssallah.cloud.spring.query.GetClientBillsQuery;
import com.farouk.bengarssallah.cloud.spring.query.GetClientNotPayedBillsQuery;
import com.farouk.bengarssallah.cloud.spring.query.GetNotPayedBillsQuery;
import com.farouk.bengarssallah.cloud.spring.repository.BillRepository;
import com.farouk.bengarssallah.cloud.spring.repository.ClientRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class BillServiceHandler {
	
	private BillRepository billRepository;
	private ClientRepository clientRepository;

	
	@EventHandler
	public void on(BillGeneratedEvent event) {
		log.info("Bill generated with Reference : " + event.getReference());
		Client client = clientRepository.getOne(event.getClientReference());
		if(client == null) throw new ClientNotFoundException("Client Not in the system");
		billRepository.save(new Bill(
				event.getReference(),
				event.getType(),
				event.getPeriod(), 
				event.getState(), 
				event.getAmount(),
                client,
				event.getGenerationDate(), 
				event.getDeadline()
			   )
		    );
	}
	
	
	@EventHandler
	public void on(BillStateChangedEvent event) {
		log.info("Bill State changing with Reference : " + event.getReference());
		Bill bill = billRepository.getOne(event.getReference());
		if(bill == null) throw new BillNotFoundException("Bill Not in the system");
		bill.setState(event.getState());
		billRepository.save(bill);
	}
	
	
	@EventHandler
	public void on(BillDeletedEvent event) {
		log.info("Bill Deleted with Reference : " + event.getReference());
		Bill bill = billRepository.getOne(event.getReference());
		if(bill == null) throw new BillNotFoundException("Bill Not in the system");
		billRepository.deleteById(event.getReference());
	}
	
	@QueryHandler
	public List<Bill> on(GetAllBillsQuery query) {
		log.info("GetAllClientsQuery received ");
		return billRepository.findAll();
	}
	
	@QueryHandler
	public Bill on(GetBillQuery query) {
		log.info("GetClientQuery received with Reference : " + query.getReference());
		Bill bill = billRepository.getOne(query.getReference());
		if(bill == null) throw new BillNotFoundException("Bill Not in the system");
		return bill;
	}
	
	
	@QueryHandler
	public List<Bill> on(GetNotPayedBillsQuery query) {
		log.info("GetNotPayedBillsQuery received");
        return billRepository.findNotPayedBills();
	}
	
	@QueryHandler
	public List<Bill> on(GetClientBillsQuery query) {
		log.info("GetClientBillsQuery received for client : " + query.getClientReference());
        return billRepository.findClientBills(query.getClientReference());
	}
	
	@QueryHandler
	public List<Bill> on(GetClientNotPayedBillsQuery query) {
		log.info("GetClientNotPayedBillsQuery received for client : " + query.getClientReference());
        return billRepository.findClientNotPayedBills(query.getClientReference());
	}

}
