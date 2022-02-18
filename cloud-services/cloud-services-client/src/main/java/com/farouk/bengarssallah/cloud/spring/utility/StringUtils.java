package com.farouk.bengarssallah.cloud.spring.utility;

import java.util.UUID;

public class StringUtils {
	
	public static UUID formatUuid(String reference) {
		reference = reference.replace("-", "");
        String formatted = String.format(
        		reference.substring(0, 8) + "-" +
        				reference.substring(8, 12) + "-" +
                        reference.substring(12, 16) + "-" +
                        reference.substring(16, 20) + "-" +
                        reference.substring(20, 32)
        );
        return UUID.fromString(formatted);
    }
	
	
	

}
