package com.farouk.bengarssallah.cloud.spring.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.farouk.bengarssallah.cloud.spring.domain.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, UUID> {
	
}
