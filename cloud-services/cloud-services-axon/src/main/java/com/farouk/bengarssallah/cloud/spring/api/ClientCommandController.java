package com.farouk.bengarssallah.cloud.spring.api;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import com.farouk.bengarssallah.cloud.spring.command.CreateClientCommand;
import com.farouk.bengarssallah.cloud.spring.command.DeleteClientCommand;
import com.farouk.bengarssallah.cloud.spring.command.UpdateClientCommand;
import com.farouk.bengarssallah.cloud.spring.dto.ClientRequestDTO;

import static com.farouk.bengarssallah.cloud.spring.utility.StringUtils.formatUuid;

import lombok.AllArgsConstructor;

@RestController
@RefreshScope
@RequestMapping("/api/command/client")
@AllArgsConstructor
public class ClientCommandController {
	
	  private CommandGateway commandGateway;
	  private EventStore eventStore;

	  @HystrixCommand(fallbackMethod = "fallback_api", commandProperties = {
        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
      })
	  @PostMapping(value = "/create", consumes=MediaType.APPLICATION_JSON_VALUE)
	  public CompletableFuture<String> createClient(@RequestBody ClientRequestDTO requestDTO) {
		  CompletableFuture<String> response = this.commandGateway.send(new CreateClientCommand(
	                UUID.randomUUID(),
	                requestDTO.getFullName(),
	                requestDTO.getEmail(),
	                new Date()
	        ));
		  return response;
	  }
	  
	  
	  @HystrixCommand(fallbackMethod = "fallback_api", commandProperties = {
        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
      })
	  @PutMapping("/put/{reference}")
	  public CompletableFuture<String> putClient(@PathVariable String reference, @RequestBody ClientRequestDTO requestDTO) {
		  CompletableFuture<String> response = this.commandGateway.send(new UpdateClientCommand(
				    formatUuid(reference),
	                requestDTO.getFullName(),
	                requestDTO.getEmail()
	        ));
		  return response;
	  }

	  
	 /* @ExceptionHandler(Exception.class)
		public ResponseEntity<String> handle(Exception exception){
			       return new ResponseEntity<>(
			    		   exception.getMessage(), 
			    		   HttpStatus.INTERNAL_SERVER_ERROR
			    	);
		}*/
	  
	  
	  @HystrixCommand(fallbackMethod = "fallback_api", commandProperties = {
        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
      })
	  @DeleteMapping("/delete/{reference}")
	  public CompletableFuture<Void> deleteClient(@PathVariable String reference) {
		  CompletableFuture<Void> response = this.commandGateway.send(new DeleteClientCommand(
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
