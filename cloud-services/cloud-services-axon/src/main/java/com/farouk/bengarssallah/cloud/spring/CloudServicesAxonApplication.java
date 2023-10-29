package com.farouk.bengarssallah.cloud.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition; 
import io.swagger.v3.oas.annotations.info.Info;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrix
@RefreshScope
@OpenAPIDefinition(
        info = @Info(
                title = "Client Bills Management System using Spring Boot AXON RESTful APIs.",
                version = "0",
                description = "blueprint system using Spring Boot cloud and Axon Framework to manage client's bills."
  ))
public class CloudServicesAxonApplication {

	public static void main(String[] args) {
        SpringApplication.run(CloudServicesAxonApplication.class, args);
    }
	
}
