package com.farouk.bengarssallah.cloud.spring.amqp;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;

public class RabbitMQConfiguration {
	
	@Bean
	public Queue helloQueue() {
	    return new Queue("Bill Upddate Notification Channel");
	}

}
