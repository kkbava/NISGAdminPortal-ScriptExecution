package com.stylopay.apiUtility;

import java.io.IOException;

import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.relevantcodes.extentreports.LogStatus;
import com.stylopay.genericUtility.Util;
import com.stylopay.reportUtility.CreateReport;

public class SendGenericEmailAPI {
	
	private static void triggerEmail(String message) throws UnirestException, IOException {
		
		Unirest.setTimeouts(0, 0);	 
		  HttpResponse<String> sendGenericEmailAPIResponse = Unirest.post(Util.getTestDataFromPropertiesFile("sendGenericEmailAPITestData", "sendGenEmailAPI_Url"))
		    .header("Content-Type", "application/json")
		    .body("{\r\n\"subject\":\""+ Util.getTestDataFromPropertiesFile("sendGenericEmailAPITestData", "emailSubject")+ "\",\r\n\"template\": \"" + Util.getTestDataFromPropertiesFile("sendGenericEmailAPITestData", "emailTemplate") + "\",\r\n\"email\":\""+ Util.getTestDataFromPropertiesFile("sendGenericEmailAPITestData", "senderEmail") + "\",\r\n\"name\": \"" + Util.getTestDataFromPropertiesFile("sendGenericEmailAPITestData", "name") + "\",\r\n\"msg\":\"" + message + "\",\r\n\"receivername\":\"" + Util.getTestDataFromPropertiesFile("sendGenericEmailAPITestData", "receiverName") + "\",\r\n\"sendername\":\"" + Util.getTestDataFromPropertiesFile("sendGenericEmailAPITestData", "senderName") + "\"\r\n}")
		    .asString();
		  
		  System.out.println(sendGenericEmailAPIResponse.getBody());
		  JSONObject sendGenEmailAPIJsonRes = new JSONObject(sendGenericEmailAPIResponse.getBody());
		  
		  if(sendGenEmailAPIJsonRes.getBoolean("status")==true) {
			  
			  CreateReport.test.log(LogStatus.PASS, "Check email triggering", "email is triggering successfully");
		  }else {
			  
			  CreateReport.test.log(LogStatus.FAIL, "Check email triggering", "email is not triggering");
		  }
	}

}
