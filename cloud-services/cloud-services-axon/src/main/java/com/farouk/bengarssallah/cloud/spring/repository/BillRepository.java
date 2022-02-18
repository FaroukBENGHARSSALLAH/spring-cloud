package com.farouk.bengarssallah.cloud.spring.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.farouk.bengarssallah.cloud.spring.domain.Bill;

@Repository
public interface BillRepository extends JpaRepository<Bill, UUID> {

	
	@Query(value="SELECT * FROM Bill b WHERE b.state = 'not_paid' ORDER BY b.generation_date ASC", nativeQuery=true)
    List<Bill> findNotPayedBills();
	
	@Query(value="SELECT * FROM Bill b WHERE b.reference_client = ?1 ORDER BY b.generation_date ASC", nativeQuery=true)
    List<Bill> findClientBills(UUID clientReference);

	@Query(value="SELECT * FROM Bill b WHERE b.reference_client = ?1 AND b.state = 'not_paid' ORDER BY b.generation_date ASC", nativeQuery=true)
    List<Bill> findClientNotPayedBills(UUID clientReference);
	
}
