# cloud-services-axon
==========================

A prototype project to start coding the axon framework based on the Event Sourcing and CQRS arcihitecture pattern.

Used APIs,

- Java 8
- Spring Boot 2.2.2
- JPA, Hibernate 5.0
- H2database
- axon-spring-boot-starter Maven Dependency
- Hystrix

Project notes

- [x] @Aggregate : basic Axon PJO
- [x] @TargetAggregateIdentifier : idenfity attribute as the key for the POJO aggregate 
- [x] @CommandHandler : speicify that the method will handle an aggregate's command
- [x] @EventSourcingHandler : speicify that the method will handle a command's event 
- [x] @EnableDiscoveryClient : auto-register the microservice in the eureka registry
- [x] @EnableFeignClients : auto-configure feign client feature
- [x] @EnableHystrix : auto-configure hystrix fault tolerance feature
- [x] @RefreshScope : enable application proporties re-loading during runtime
- [x] @OpenAPIDefinition : OpenAPI3 Documentation feature

> This project is in the development stage
