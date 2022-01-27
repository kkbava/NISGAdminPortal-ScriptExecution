package com.stylopay.apiUtility;

import java.io.IOException;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.stylopay.genericUtility.Util;

public class GetWalletBalanceAPI {
	
	private static HashMap<String, String> walletInfoFromAPI = new HashMap<>();	
	

	private HashMap<String, String> getWalletBalAPIInfo() throws UnirestException, IOException{		  
		  
		  Unirest.setTimeouts(0, 0);
		  HttpResponse<String> getWalletBalanceAPIResponse = Unirest.get(Util.getTestDataFromPropertiesFile("getWalletBalAPITestData", "getWalletBalAPI_UrlLeftPart") + Util.getTestDataFromPropertiesFile("getWalletBalAPITestData", "getWallBalAPIcustHashID") + "/" + Util.getTestDataFromPropertiesFile("getWalletBalAPITestData", "getWalBalAPIwalletHashID") + Util.getTestDataFromPropertiesFile("getWalletBalAPITestData", "getWalletBalAPI_UrlRightPart"))
				  .header("x-api-key", Util.getTestDataFromPropertiesFile("getWalletBalAPITestData", "getWalBalAPI-x-api-key"))
				  .header("clientHasID", Util.getTestDataFromPropertiesFile("getWalletBalAPITestData", "getWalBalAPI_clientHashID"))
				  .header("Authorization", "Bearer " + GetAuthTokenAPI.getAccessToken())
				  .asString();	  
		  
		  JSONArray getWalletBalJsonArray = new JSONArray(getWalletBalanceAPIResponse.getBody());
		  
		  
		  for(int i = 0; i<getWalletBalJsonArray.length(); i++) {
			  
			  String currSymbol = getWalletBalJsonArray.getJSONObject(i).getString("curSymbol");		  
			  double balance = getWalletBalJsonArray.getJSONObject(i).getDouble("balance");		  
			  
			  walletInfoFromAPI.put(currSymbol, Double.toString(balance));
		  }	  

		  
		return walletInfoFromAPI; 
		  
	  }
	
	public static HashMap<String, String> getWalletInfoFromAPI() {
		return walletInfoFromAPI;
	}

	public static void setWalletInfoFromAPI(HashMap<String, String> walletInfoFromAPI) throws UnirestException, IOException {
		GetWalletBalanceAPI.walletInfoFromAPI = new GetWalletBalanceAPI().getWalletBalAPIInfo();
	}

}
