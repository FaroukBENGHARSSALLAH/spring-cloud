# cloud-services-client
==========================

The client's microservice project.

Used APIs,

- Java 8
- Spring Boot 2.2.2
- JPA, Hibernate 5.0
- H2database
- spring-cloud-starter-config Maven Depedency
- spring-cloud-starter-netflix-eureka-client Maven dependency
- spring-cloud-starter-openfeign Maven dependency
- Feign Client
- RabbitMQ
- RestTemeplate
- Hystrix

Project notes

- [x] @RabbitListener : attach a listener event to the RabbitMQ queue
- [x] @EnableDiscoveryClient : auto-register the microservice in the eureka registry
- [x] @EnableFeignClients : auto-configure feign client feature
- [x] @EnableHystrix : auto-configure hystrix fault tolerance feature
- [x] @RefreshScope : enable application proporties re-loading during runtime
- [x] @OpenAPIDefinition : OpenAPI3 Documentation feature

> This project is in the development stage
