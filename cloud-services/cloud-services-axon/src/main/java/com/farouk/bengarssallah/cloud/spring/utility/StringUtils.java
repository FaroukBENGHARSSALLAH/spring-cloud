package com.farouk.bengarssallah.cloud.spring.utility;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
	
	
	public static String generatePeriod(String type) {
		                      LocalDate date = LocalDate.now();
                              switch(type){
                            	  case "annual" : {
                            		   return (date.getYear() - 1) + " - " + date.getYear();
                            	  }
                            	  case "quarter" : {
                            		   DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM");
                            		   return formatter.format(date.minusMonths(3)) + "-" + formatter.format(date);
                            	  }
                            	  default : {
                            		  return "";
                            	  }
                              }
	                  }
	
	
	
	
	public static Date generateDeadline(String type) {
		        LocalDate date = LocalDate.now();
		        switch(type){
		      	  case "annual" : {
		      		   return new Date(date.plusMonths(6).toEpochDay());
		      	  }
		      	  case "quarter" : {
		      		   return new Date(date.plusMonths(1).toEpochDay());
		      	  }
		      	  default : {
		      		  return new Date();
		      	  }
		        }
}

}
