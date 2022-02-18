package com.farouk.bengarssallah.cloud.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GenerateBillRequestDTO {

	 private String type;
	 private double amount;
	 private String clientReference;
	
}
