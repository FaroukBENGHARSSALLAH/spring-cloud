package com.farouk.bengarssallah.cloud.spring;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import com.farouk.bengarssallah.cloud.spring.domain.Bill;
import com.farouk.bengarssallah.cloud.spring.repository.BillRepository;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import io.swagger.v3.oas.annotations.OpenAPIDefinition; 
import io.swagger.v3.oas.annotations.info.Info;

@EnableDiscoveryClient
@SpringBootApplication
@EnableHystrix
@RefreshScope
@OpenAPIDefinition(
        info = @Info(
                title = "Bill Management System using Spring Boot cloud RESTful APIs.",
                version = "0",
                description = "blueprint system using Spring Boot cloud to manage bills."
  ))
public class CloudServicesBillApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudServicesBillApplication.class, args);
	}
	@Bean
	CommandLineRunner start(BillRepository clientRepository) {
		return args->{
			Stream.of("annual","quarter").forEach(typ->{
				clientRepository.save(new Bill(UUID.randomUUID(), typ, "2022-2023", "generated", UUID.randomUUID(), 
						new Date(), new Date()));
			});
			clientRepository.findAll().forEach(System.out::println);
		};
	}
}
