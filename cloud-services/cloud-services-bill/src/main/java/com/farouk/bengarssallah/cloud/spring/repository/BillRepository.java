package com.farouk.bengarssallah.cloud.spring.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.farouk.bengarssallah.cloud.spring.domain.Bill;

@Repository
public interface BillRepository extends JpaRepository<Bill, UUID> {
	
}
