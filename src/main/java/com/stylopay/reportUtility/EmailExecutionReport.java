package com.stylopay.reportUtility;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class EmailExecutionReport {
	
	String reportDetails="";
	
	public void sendEmail(HashMap<String, String> errorMessage) throws EmailException {
		
		try {
			
			System.out.println("Sending email");
			
			Email email = new SimpleEmail();
			email.setHostName("smtp.gmail.com");
			email.setSmtpPort(465);
			email.setAuthenticator(new DefaultAuthenticator("testQAMail2022@gmail.com", "Demo@1234"));
			email.setSSLOnConnect(true);
			email.setFrom("testQAMail2022@gmail.com");
			email.setSubject("Nium Admin Daily Execution Report Details");
			
			for(Map.Entry<String, String> map: errorMessage.entrySet()) {
				
				reportDetails = reportDetails + "TestCase Execution Result: " + map.getKey() + ", Status: " + map.getValue() + "\n\n";				
				
			}					
			email.setMsg(reportDetails);		
			email.addTo("testQAMail2022@gmail.com");
			email.send();
			
			System.out.println("email sent");
			
		}catch(Exception e) {
			
			System.out.println("Email sending error: " + e.getMessage());
		}
		
	}

}
