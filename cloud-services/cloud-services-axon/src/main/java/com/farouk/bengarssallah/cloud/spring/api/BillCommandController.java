package com.farouk.bengarssallah.cloud.spring.api;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
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
import org.springframework.cloud.context.config.annotation.RefreshScope;

import com.farouk.bengarssallah.cloud.spring.command.DeleteBillCommand;
import com.farouk.bengarssallah.cloud.spring.command.GenerateBillCommand;
import com.farouk.bengarssallah.cloud.spring.command.UpdateBillStateCommand;
import com.farouk.bengarssallah.cloud.spring.dto.GenerateBillRequestDTO;
import com.farouk.bengarssallah.cloud.spring.dto.UpdateBillStatelRequestDTO;
import com.farouk.bengarssallah.cloud.spring.utility.BillStates;

import static com.farouk.bengarssallah.cloud.spring.utility.StringUtils.formatUuid;
import static com.farouk.bengarssallah.cloud.spring.utility.StringUtils.generatePeriod;
import static com.farouk.bengarssallah.cloud.spring.utility.StringUtils.generateDeadline


import lombok.AllArgsConstructor;

@RestController
@RefreshScope
@RequestMapping("/api/command/bill")
@AllArgsConstructor
public class BillCommandController {
	
	  private CommandGateway commandGateway;
	  private EventStore eventStore;

      @HystrixCommand(fallbackMethod = "fallback_api", commandProperties = {
        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
      })
	  @PostMapping(value = "/create", consumes=MediaType.APPLICATION_JSON_VALUE)
	  public CompletableFuture<String> generateBill(@RequestBody GenerateBillRequestDTO requestDTO) {
		  Date current = new Date();
		  CompletableFuture<String> response = this.commandGateway.send(new GenerateBillCommand(
				  UUID.randomUUID(), 
				  requestDTO.getType(), 
				  generatePeriod(requestDTO.getType()), 
				  BillStates.GENERATED, 
				  requestDTO.getAmount(),
				  formatUuid(requestDTO.getClientReference()), 
				  current, 
				  generateDeadline(requestDTO.getType())
		    ));
		  return response;
	  }
	  
	  
	  @HystrixCommand(fallbackMethod = "fallback_api", commandProperties = {
        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
      })
	  @PutMapping("/put/{reference}")
	  public CompletableFuture<String> putBill(@PathVariable String reference, @RequestBody UpdateBillStatelRequestDTO requestDTO) {
		  CompletableFuture<String> response = this.commandGateway.send(new UpdateBillStateCommand(
				    formatUuid(reference),
	                requestDTO.getState()
	        ));
		  return response;
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
	  @DeleteMapping("/delete/{reference}")
	  public CompletableFuture<Void> deleteBill(@PathVariable String reference) {
		  CompletableFuture<Void> response = this.commandGateway.send(new DeleteBillCommand(
				  formatUuid(reference)
	        ));
		  return response;
	  }

	  
	  @HystrixCommand(fallbackMethod = "fallback_api", commandProperties = {
        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
      })
	  @GetMapping("/events/{reference}")
	  public Stream get(@PathVariable String reference) {
		  return eventStore.readEvents(reference).asStream();
	  }
	  
	  private String fallback_api() {
         return "Request fails. It takes long time to response";
      }
	  
}
