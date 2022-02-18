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
public class Client {
	
	@Id
	private UUID reference;
	private String fullName;
	private String email;
	private Date creationDate;
	private int unpaidBills;

}
