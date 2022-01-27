package com.stylopay.reportUtility;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;

import com.stylopay.genericUtility.Util;

public class SendEmailExecutionReportWithAttachment {
	
   public void sendEmailWithAttachment(String message) throws EmailException {
		
		try {
			
			System.out.println("Sending email");
			
			// Create the attachment
			  EmailAttachment attachment = new EmailAttachment();
			  attachment.setPath(Util.getEmailConfigProperties().getProperty("fileToBeSent_Path"));
			  attachment.setDisposition(EmailAttachment.ATTACHMENT);
			  attachment.setDescription("Error Report Details");
			  attachment.setName("Error Report Details");

			  // Create the email message
			  MultiPartEmail email = new MultiPartEmail();
			  email.setHostName(Util.getEmailConfigProperties().getProperty("setEmailHostName"));
			  email.setSmtpPort(465);
			  email.setAuthenticator(new DefaultAuthenticator(Util.getEmailConfigProperties().getProperty("authenticator_UserName"), Util.getEmailConfigProperties().getProperty("authenticator_Password")));
				email.setSSLOnConnect(true);
				email.setFrom(Util.getEmailConfigProperties().getProperty("email_From"));
				email.setSubject(Util.getEmailConfigProperties().getProperty("email_Subject"));
				email.setMsg(message);
				email.addTo(Util.getEmailConfigProperties().getProperty("email_AddTo"));

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
