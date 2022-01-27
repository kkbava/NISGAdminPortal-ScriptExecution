package com.stylopay.reportUtility;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;

public class SendEmailExecutionReportWithAttachment {
	
   public void sendEmailWithAttachment() throws EmailException {
		
		try {
			
			System.out.println("Sending email");
			
			// Create the attachment
			  EmailAttachment attachment = new EmailAttachment();
			  attachment.setPath("./test-output/emailable-report.html");
			  attachment.setDisposition(EmailAttachment.ATTACHMENT);
			  attachment.setDescription("Error Report Details");
			  attachment.setName("Error Report Details");

			  // Create the email message
			  MultiPartEmail email = new MultiPartEmail();
			  email.setHostName("smtp.gmail.com");
			  email.setSmtpPort(465);
			  email.setAuthenticator(new DefaultAuthenticator("testQAMail2022@gmail.com", "Demo@1234"));
				email.setSSLOnConnect(true);
				email.setFrom("testQAMail2022@gmail.com");
				email.setSubject("Nium Admin Daily Execution Report");
				email.setMsg("Error description");
				email.addTo("testQAMail2022@gmail.com");

			  // add the attachment
			  email.attach(attachment);

			  // send the email
			  email.send();
			
			System.out.println("email sent with attachment");
			
		}catch(Exception e) {
			
			System.out.println("Email sending error: " + e.getMessage());
		}
		
	}

}
