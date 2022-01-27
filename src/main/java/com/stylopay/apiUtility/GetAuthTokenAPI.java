package com.stylopay.apiUtility;

import java.io.IOException;

import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.stylopay.genericUtility.Util;

public class GetAuthTokenAPI {
	
	private static String accessToken;	
	

	private static String getAuthTokenAPIInfo() throws UnirestException, IOException {
		
		Unirest.setTimeouts(0, 0);
		  HttpResponse<String> getTokenAPIResponse = Unirest.post(Util.getTestDataFromPropertiesFile("getAuthTokenAPITestData", "getAuthTokenAPI_Url"))
		    .header("Content-Type", Util.getTestDataFromPropertiesFile("getAuthTokenAPITestData", "getAuthTokenAPI_Content_Type"))
		    .header("x-api-key", Util.getTestDataFromPropertiesFile("getAuthTokenAPITestData", "getAuthTokenAPI-x-api-key"))
		    .header("Authorization", Util.getTestDataFromPropertiesFile("getAuthTokenAPITestData", "getAuthTokenAPI_AuthID"))
		    .field("username", Util.getTestDataFromPropertiesFile("loginTestData", "loginEmail"))
		    .field("password", Util.getTestDataFromPropertiesFile("loginTestData", "loginPassword"))
		    .field("grant_type", Util.getTestDataFromPropertiesFile("getAuthTokenAPITestData", "getAuthTokenAPI_grant_type"))
		    .asString();

		  JSONObject getTokenAPIJsonResponse = new JSONObject(getTokenAPIResponse.getBody());	  
		  String accessToken = getTokenAPIJsonResponse.getString("access_token");
		  
		  return accessToken;
	}
	
	public static String getAccessToken() {
		return accessToken;
	}

	public static void setAccessToken(String accessToken) throws UnirestException, IOException {
		GetAuthTokenAPI.accessToken = new GetAuthTokenAPI().getAuthTokenAPIInfo();
	}

}
