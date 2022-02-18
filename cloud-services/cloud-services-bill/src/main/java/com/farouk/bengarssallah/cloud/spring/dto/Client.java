package com.farouk.bengarssallah.cloud.spring.dto;

import java.util.Date;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client {
	
	private UUID reference;
	private String fullName;
	private String email;
	private Date creationDate;
	private int unpaidBills;


}
