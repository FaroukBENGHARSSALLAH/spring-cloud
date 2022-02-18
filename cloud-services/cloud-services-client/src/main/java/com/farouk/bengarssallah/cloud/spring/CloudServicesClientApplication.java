package com.farouk.bengarssallah.cloud.spring;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import com.farouk.bengarssallah.cloud.spring.domain.Client;
import com.farouk.bengarssallah.cloud.spring.repository.ClientRepository;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import io.swagger.v3.oas.annotations.OpenAPIDefinition; 
import io.swagger.v3.oas.annotations.info.Info;

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
@EnableHystrix
@RefreshScope
@OpenAPIDefinition(
        info = @Info(
                title = "Client Management System using Spring Boot cloud RESTful APIs.",
                version = "0",
                description = "blueprint system using Spring Boot cloud to manage clients."
  ))
public class CloudServicesClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudServicesClientApplication.class, args);
	}
	@Bean
	CommandLineRunner start(ClientRepository clientRepository) {
		return args->{
			Stream.of("First","Second","Third").forEach(cn->{
				clientRepository.save(new Client(UUID.randomUUID(), cn, cn+"@gmail.com", new Date()));
			});
			clientRepository.findAll().forEach(System.out::println);
		};
	}
}
