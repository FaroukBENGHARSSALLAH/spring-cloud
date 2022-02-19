# cloud-config
==========================

Configuration server used to keep the location of the spring boot cloud projects properties files in a predefined location.

Used APIs,

- Java 8
- Spring Boot 2.2.2
- spring-cloud-config-server Maven Dependency

Project notes

- [x] @EnableConfigServer : will auto-configure the project as a config server
- Every part except the config server has a properties file named bootstrap.properties in its resources folder, as it has a predefined properties file with the project's name in config server's default location for keeping the properties files.


> This project is in the development stage
