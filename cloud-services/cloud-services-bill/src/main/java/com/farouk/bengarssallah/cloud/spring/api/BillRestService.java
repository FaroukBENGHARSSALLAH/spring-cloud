package com.farouk.bengarssallah.cloud.spring.api;

import static com.farouk.bengarssallah.cloud.spring.utility.StringUtils.formatUuid;
import static com.farouk.bengarssallah.cloud.spring.utility.StringUtils.generateDeadline;
import static com.farouk.bengarssallah.cloud.spring.utility.StringUtils.generatePeriod;

import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.farouk.bengarssallah.cloud.spring.domain.Bill;
import com.farouk.bengarssallah.cloud.spring.dto.Client;
import com.farouk.bengarssallah.cloud.spring.dto.GenerateBillRequestDTO;
import com.farouk.bengarssallah.cloud.spring.dto.PaymentNotification;
import com.farouk.bengarssallah.cloud.spring.dto.UpdateBillStatelRequestDTO;
import com.farouk.bengarssallah.cloud.spring.exception.ClientNotFoundException;
import com.farouk.bengarssallah.cloud.spring.service.BillService;
import com.farouk.bengarssallah.cloud.spring.utility.BillStates;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import lombok.AllArgsConstructor;

@RefreshScope
@AllArgsConstructor
@RestController
@RequestMapping("/api/bill")
public class BillRestService {
	
	  private BillService billService;
	  private RestTemplate restTemplate;
	  private RabbitTemplate rabbitTemplate;
		
	  @HystrixCommand(fallbackMethod = "fallback_api", commandProperties = {
        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
      })	
	  @PostMapping(value = "/generate", consumes=MediaType.APPLICATION_JSON_VALUE)
	  public ResponseEntity<Bill> createBill(@RequestBody GenerateBillRequestDTO requestDTO) throws Exception {
		            final String baseUrl = "http://localhost:8080/cloud-services-client/api/client/get/" + requestDTO.getClientReference();
		            URI uri = new URI(baseUrl);
		            ResponseEntity<Client> result = restTemplate.getForEntity(uri, Client.class);
		            if(!result.hasBody()) throw new ClientNotFoundException("Client Not in the system");
		            Bill bill = billService.addBill(new Bill(UUID.randomUUID(), requestDTO.getType(), generatePeriod(requestDTO.
		            		getType()), BillStates.GENERATED, formatUuid(requestDTO.getClientReference()), 
		            		new Date(), generateDeadline(requestDTO.getType())));
		            return new ResponseEntity<Bill>(bill, HttpStatus.OK);
	       }
	  
	  
	  @HystrixCommand(fallbackMethod = "fallback_api", commandProperties = {
        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
      })
	  @PutMapping("/put/{reference}")
	  public ResponseEntity<Bill> putBill(@PathVariable String reference, @RequestBody UpdateBillStatelRequestDTO requestDTO) {
		  Bill bill = billService.putBill(formatUuid(reference), requestDTO.getState());
		  if(requestDTO.getState().equals(BillStates.PAID))
		      rabbitTemplate.convertAndSend("Bill Upddate Notification Channel", new PaymentNotification(reference, 
		    		  bill.getClientReference().toString(), new Date()));
          return new ResponseEntity<Bill>(bill, HttpStatus.OK);
	  }

	 
      @HystrixCommand(fallbackMethod = "fallback_api", commandProperties = {
        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
      })	 
	  @DeleteMapping("/delete/{reference}")
	  public ResponseEntity<Void> deleteBill(@PathVariable String reference) {
           billService.deleteBill(formatUuid(reference));
           return new ResponseEntity<>(HttpStatus.OK);
	  }
	  
	  @HystrixCommand(fallbackMethod = "fallback_api", commandProperties = {
        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
      })
	  @GetMapping("/get/all")
	  public ResponseEntity<List> getAllBills() {
           List<Bill> bills = billService.getAllBills();
           return new ResponseEntity<>(bills, HttpStatus.OK);
	  }
	  
	  @HystrixCommand(fallbackMethod = "fallback_api", commandProperties = {
        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
      })
	  @GetMapping("/get/{reference}")
	  public ResponseEntity<Bill> getBillByReference(@PathVariable String reference) {
           Bill bill = billService.getBillByReference(formatUuid(reference));
           return new ResponseEntity<>(bill, HttpStatus.OK);
	  }
	  
	  @ExceptionHandler(Exception.class)
	  public ResponseEntity<String> handle(Exception exception){
			       return new ResponseEntity<>(
			    		   exception.getMessage(), 
			    		   HttpStatus.INTERNAL_SERVER_ERROR
			    	);
		}
	  
	  private String fallback_api() {
         return "Request fails. It takes long time to response";
      }
	
}
