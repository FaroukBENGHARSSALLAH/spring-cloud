package com.farouk.bengarssallah.cloud.spring.domain;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Bill {
	
	 @Id
	 private UUID reference;
	 private String type;
	 private String period;
	 private String state;
	 private double amount;
	 @OneToOne
	 @JoinColumn(name = "reference_client")
	 private Client client;
	 private Date generationDate;
	 private Date deadline;

}
