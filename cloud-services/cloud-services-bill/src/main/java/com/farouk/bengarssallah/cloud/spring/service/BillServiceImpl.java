package com.farouk.bengarssallah.cloud.spring.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.farouk.bengarssallah.cloud.spring.domain.Bill;
import com.farouk.bengarssallah.cloud.spring.exception.BillNotFoundException;
import com.farouk.bengarssallah.cloud.spring.repository.BillRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class BillServiceImpl  implements BillService {
		
	private BillRepository billRepository;
	
	public Bill addBill(Bill bill) {
		log.info("Bill Created with Reference : " + bill.getReference());
		return billRepository.save(bill);
	}
	
	
	public Bill putBill(UUID reference, String state) {
		log.info("Bill Updated with Reference : " + reference);
		Bill persistedBill = billRepository.getOne(reference);
		if(persistedBill == null) throw new BillNotFoundException("Bill Not in the system");
		persistedBill.setState(state);
		return billRepository.save(persistedBill);
	}
	
	
	public void deleteBill(UUID reference) {
		log.info("Bill Deleted with Reference : " + reference);
		Bill bill = billRepository.getOne(reference);
		if(bill == null) throw new BillNotFoundException("Bill Not in the system");
		billRepository.deleteById(reference);
	}
	
	public List<Bill> getAllBills() {
		log.info("GetAllBillsQuery received ");
		return billRepository.findAll();
	}
	
	public Bill getBillByReference(UUID reference) {
		log.info("GetBillQuery received with Reference : " + reference);
		Bill bill = billRepository.getOne(reference);
		if(bill == null) throw new BillNotFoundException("Bill Not in the system");
		return bill;
	}

}
