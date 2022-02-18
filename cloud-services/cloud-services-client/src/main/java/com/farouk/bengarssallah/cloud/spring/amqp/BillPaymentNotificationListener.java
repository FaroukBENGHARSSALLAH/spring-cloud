package com.farouk.bengarssallah.cloud.spring.amqp;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.farouk.bengarssallah.cloud.spring.dto.PaymentNotification;
import com.farouk.bengarssallah.cloud.spring.service.ClientService;

import lombok.AllArgsConstructor;

import static com.farouk.bengarssallah.cloud.spring.utility.StringUtils.formatUuid;

@Component
@AllArgsConstructor
public class BillPaymentNotificationListener {
	
	private ClientService clientService;
	
	@RabbitListener(queues = "Bill Upddate Notification Channel")
	public void updateClientUnpaidBills(PaymentNotification notification) {
		clientService.putClientUnpaidBills(formatUuid(notification.getClientReference()));
	}


}
