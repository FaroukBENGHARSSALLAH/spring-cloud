package com.farouk.bengarssallah.cloud.spring.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BillConfiguration {
	
	@Bean
	public Queue billUpddateQueue() {
	    return new Queue("Bill Upddate Notification Channel");
	}
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
	    return builder.build();
	}

}
