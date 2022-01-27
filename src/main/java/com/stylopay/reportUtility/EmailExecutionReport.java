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

import com.stylopay.genericUtility.Util;

public class EmailExecutionReport {
	
	String reportDetails="";
	
	public void sendEmail(HashMap<String, String> errorMessage) throws EmailException {
		
		try {
			
			System.out.println("Sending email");
			
			Email email = new SimpleEmail();
			email.setHostName(Util.getEmailConfigProperties().getProperty("setEmailHostName"));
			email.setSmtpPort(465);
			email.setAuthenticator(new DefaultAuthenticator(Util.getEmailConfigProperties().getProperty("authenticator_UserName"), Util.getEmailConfigProperties().getProperty("authenticator_Password")));
			email.setSSLOnConnect(true);
			email.setFrom(Util.getEmailConfigProperties().getProperty("email_From"));
			email.setSubject(Util.getEmailConfigProperties().getProperty("email_Subject"));
			
			for(Map.Entry<String, String> map: errorMessage.entrySet()) {
				
				reportDetails = reportDetails + "TestCase Execution Result: " + map.getKey() + ", Status: " + map.getValue() + "\n\n";				
				
			}					
			email.setMsg(reportDetails);		
			email.addTo(Util.getEmailConfigProperties().getProperty("email_AddTo"));
			email.send();
			
			System.out.println("email sent");
			
		}catch(Exception e) {
			
			System.out.println("Email sending error: " + e.getMessage());
		}
		
	}

}
