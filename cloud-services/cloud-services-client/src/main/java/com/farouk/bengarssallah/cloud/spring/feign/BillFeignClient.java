package com.farouk.bengarssallah.cloud.spring.feign;

import java.util.List;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.farouk.bengarssallah.cloud.spring.dto.Bill;

@FeignClient(name = "cloud-services-bill")
public interface BillFeignClient {

	@GetMapping("/bill/client/{reference}")
	public List<Bill> getClientBills(UUID reference);
	
	@GetMapping("/bill/client/unpaid/{reference}")
	public List<Bill> getClientUnpaidBills(UUID reference);
	
}
