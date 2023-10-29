package com.farouk.bengarssallah.cloud.spring.domain;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;

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
	 private UUID clientReference;
	 private Date generationDate;
	 private Date deadline;

}
