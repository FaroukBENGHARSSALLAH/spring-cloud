package com.farouk.bengarssallah.cloud.spring.service;

import java.util.List;
import java.util.UUID;

import com.farouk.bengarssallah.cloud.spring.domain.Bill;

public interface BillService {
	
	
	public Bill addBill(Bill bill);
	
	public Bill putBill(UUID reference, String state);

	public void deleteBill(UUID reference);
	
	public List<Bill> getAllBills();
	
	public Bill getBillByReference(UUID reference);

	

}
