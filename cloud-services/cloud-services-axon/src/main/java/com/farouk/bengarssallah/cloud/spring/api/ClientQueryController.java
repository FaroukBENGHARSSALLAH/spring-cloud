package com.farouk.bengarssallah.cloud.spring.api;

import java.util.List;

import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import com.farouk.bengarssallah.cloud.spring.domain.Client;
import com.farouk.bengarssallah.cloud.spring.query.GetAllClientsQuery;
import com.farouk.bengarssallah.cloud.spring.query.GetClientQuery;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import lombok.AllArgsConstructor;

import static com.farouk.bengarssallah.cloud.spring.utility.StringUtils.formatUuid;

@RestController
@RefreshScope
@RequestMapping("/api/query/client")
@AllArgsConstructor
public class ClientQueryController {

	 private QueryGateway queryGateway;
	 
	 @HystrixCommand(fallbackMethod = "fallback_api", commandProperties = {
        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
     })
	 @GetMapping("/all")
	 public List<Client> listClients(){
		  return queryGateway.query(
				   new GetAllClientsQuery(), 
				   ResponseTypes.multipleInstancesOf(Client.class)
				 ).join();
	 }
	 
	 @HystrixCommand(fallbackMethod = "fallback_api", commandProperties = {
        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
     })
	 @GetMapping("/{reference}")
	 public Client findClient(@PathVariable String reference){
		  Client client =  queryGateway.query(
				   new GetClientQuery(formatUuid(reference)), 
				   ResponseTypes.instanceOf(Client.class)
				 ).join();
		  return client;
	 }
	 
	 private String fallback_api() {
         return "Request fails. It takes long time to response";
     }
	 
}
