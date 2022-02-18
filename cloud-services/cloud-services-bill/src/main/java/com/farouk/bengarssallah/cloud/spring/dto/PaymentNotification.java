package com.farouk.bengarssallah.cloud.spring.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentNotification {
	
	private String billReference;
	private String clientReference;
	private Date paymentDate;

}
