package com.farouk.bengarssallah.cloud.spring.query;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class GetClientNotPayedBillsQuery extends BaseQuery {
	
	@Getter private UUID clientReference;

	
}
