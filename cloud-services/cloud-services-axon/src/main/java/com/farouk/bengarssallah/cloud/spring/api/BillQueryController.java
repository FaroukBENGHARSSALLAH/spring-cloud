package com.farouk.bengarssallah.cloud.spring.api;

import java.util.List;

import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import com.farouk.bengarssallah.cloud.spring.domain.Bill;
import com.farouk.bengarssallah.cloud.spring.query.GetAllBillsQuery;
import com.farouk.bengarssallah.cloud.spring.query.GetBillQuery;
import com.farouk.bengarssallah.cloud.spring.query.GetClientBillsQuery;
import com.farouk.bengarssallah.cloud.spring.query.GetClientNotPayedBillsQuery;
import com.farouk.bengarssallah.cloud.spring.query.GetNotPayedBillsQuery;

import lombok.AllArgsConstructor;

import static com.farouk.bengarssallah.cloud.spring.utility.StringUtils.formatUuid;

@RestController
@RefreshScope
@RequestMapping("/api/query/bill")
@AllArgsConstructor
public class BillQueryController {

	 private QueryGateway queryGateway;
	 
	 @HystrixCommand(fallbackMethod = "fallback_api", commandProperties = {
        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
      })
	 @GetMapping("/all")
	 public List<Bill> listBills(){
		  return queryGateway.query(
				   new GetAllBillsQuery(), 
				   ResponseTypes.multipleInstancesOf(Bill.class)
				 ).join();
	 }
	 
	 @HystrixCommand(fallbackMethod = "fallback_api", commandProperties = {
        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
      })
	 @GetMapping("/{reference}")
	 public Bill findBill(@PathVariable String reference){
		  Bill bill =  queryGateway.query(
				   new GetBillQuery(formatUuid(reference)), 
				   ResponseTypes.instanceOf(Bill.class)
				 ).join();
		  return bill;
	 }
	 
	 @HystrixCommand(fallbackMethod = "fallback_api", commandProperties = {
        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
      })
	 @GetMapping("/notpaid/")
	 public List<Bill> findNotPaidBills(@PathVariable String reference){
		 return queryGateway.query(
				   new GetNotPayedBillsQuery(), 
				   ResponseTypes.multipleInstancesOf(Bill.class)
				 ).join();
	 }
	 
	 @HystrixCommand(fallbackMethod = "fallback_api", commandProperties = {
        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
      })
	 @GetMapping("/client/{reference}")
	 public List<Bill> findClientBills(@PathVariable String reference){
		 return queryGateway.query(
				   new GetClientBillsQuery(formatUuid(reference)), 
				   ResponseTypes.multipleInstancesOf(Bill.class)
				 ).join();
	 }
	 
	 @HystrixCommand(fallbackMethod = "fallback_api", commandProperties = {
        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
      })
	 @GetMapping("/client/notpaid/{reference}")
	 public List<Bill> findClientNotPaidBills(@PathVariable String reference){
		 return queryGateway.query(
				   new GetClientNotPayedBillsQuery(formatUuid(reference)), 
				   ResponseTypes.multipleInstancesOf(Bill.class)
				 ).join();
	 }
	 
	 private String fallback_api() {
         return "Request fails. It takes long time to response";
     }
	 
}
