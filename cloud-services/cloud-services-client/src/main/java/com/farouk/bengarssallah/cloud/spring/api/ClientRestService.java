package com.farouk.bengarssallah.cloud.spring.api;


import java.util.Date;
import java.util.List;
import java.util.UUID;

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

import com.farouk.bengarssallah.cloud.spring.domain.Client;
import com.farouk.bengarssallah.cloud.spring.dto.Bill;
import com.farouk.bengarssallah.cloud.spring.dto.ClientRequestDTO;
import com.farouk.bengarssallah.cloud.spring.feign.BillFeignClient;
import com.farouk.bengarssallah.cloud.spring.service.ClientService;

import lombok.AllArgsConstructor;

import static com.farouk.bengarssallah.cloud.spring.utility.StringUtils.formatUuid;

@RefreshScope
@AllArgsConstructor
@RestController
@RequestMapping("/api/client")
public class ClientRestService {
	
	  private ClientService clientService;
	  private BillFeignClient billFeignClient;
		
	  @HystrixCommand(fallbackMethod = "fallback_api", commandProperties = {
        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
      })	
	  @PostMapping(value = "/create", consumes=MediaType.APPLICATION_JSON_VALUE)
	  public ResponseEntity<Client> createClient(@RequestBody ClientRequestDTO requestDTO) {
		            Client client = clientService.addClient(new Client(UUID.randomUUID(), requestDTO.getFullName(), 
		            		requestDTO.getEmail(), new Date(), 0));
		            return new ResponseEntity<Client>(client, HttpStatus.OK);
	       }
	  
	  
	  @HystrixCommand(fallbackMethod = "fallback_api", commandProperties = {
        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
      })
	  @PutMapping("/put/{reference}")
	  public ResponseEntity<Client> putClient(@PathVariable String reference, @RequestBody ClientRequestDTO requestDTO) {
		  Client client = clientService.putClient(new Client(formatUuid(reference), requestDTO.getFullName(), 
          		requestDTO.getEmail(), new Date()));
          return new ResponseEntity<Client>(client, HttpStatus.OK);
	  }

	 	
      @HystrixCommand(fallbackMethod = "fallback_api", commandProperties = {
        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
      })		
	  @DeleteMapping("/delete/{reference}")
	  public ResponseEntity<Void> deleteClient(@PathVariable String reference) {
           clientService.deleteClient(formatUuid(reference));
           return new ResponseEntity<>(HttpStatus.OK);
	  }
	  
	  @GetMapping("/get/all")
	  public ResponseEntity<List> getAllClients() {
           List<Client> clients = clientService.getAllClients();
           return new ResponseEntity<>(clients, HttpStatus.OK);
	  }
	  
	  @HystrixCommand(fallbackMethod = "fallback_api", commandProperties = {
        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
      })
	  @GetMapping("/get/{reference}")
	  public ResponseEntity<Client> getClientByReference(@PathVariable String reference) {
           Client client = clientService.getClientByReference(formatUuid(reference));
           return new ResponseEntity<>(client, HttpStatus.OK);
	  }
	  
	  @ExceptionHandler(Exception.class)
	  public ResponseEntity<String> handle(Exception exception){
			       return new ResponseEntity<>(
			    		   exception.getMessage(), 
			    		   HttpStatus.INTERNAL_SERVER_ERROR
			    	);
		}
	  
	  @HystrixCommand(fallbackMethod = "fallback_api", commandProperties = {
        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
      })
	  @GetMapping("/bill/get/all/{reference}")
	  public ResponseEntity<List> getAllClientBills(@PathVariable String reference) {
           List<Bill> bills = billFeignClient.getClientBills(formatUuid(reference));
           return new ResponseEntity<>(bills, HttpStatus.OK);
	  }
	  
	  @HystrixCommand(fallbackMethod = "fallback_api", commandProperties = {
        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
      })
	  @GetMapping("/bill/get/all/unpaid/{referenece}")
	  public ResponseEntity<List> getAllClientUnpaidBills(@PathVariable String reference) {
           List<Bill> bills = billFeignClient.getClientUnpaidBills(formatUuid(reference));
           return new ResponseEntity<>(bills, HttpStatus.OK);
	  }
	  
	  private String fallback_api() {
         return "Request fails. It takes long time to response";
      }
	
}
