package com.farouk.bengarssallah.cloud.spring.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
public class GetBillQuery extends BaseQuery {

	@Getter private UUID reference;
	
}
