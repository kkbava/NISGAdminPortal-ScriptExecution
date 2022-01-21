package com.stylopay.script;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.gson.JsonArray;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.relevantcodes.extentreports.LogStatus;
import com.stylopay.driver.ManageTestSuites;
import com.stylopay.genericUtility.Util;
import com.stylopay.genericUtility.Web;
import com.stylopay.pageUtility.LoginPage;
import com.stylopay.pageUtility.UserAccount;
import com.stylopay.reportUtility.CreateReport;

public class DoDailyScriptExecution extends ManageTestSuites{
	
	WebElement userRecordTable;	
	int noOfUserRecords;
	WebElement searchedUserTable;
	String expectedAccDetailsPageName;
	String actualAccDetailsPageName; 
	
	HashMap<String, String> displayedWalletInfo = new HashMap<>();
	WebElement userWalletInfoTable;
	int noOfWalletInfo;
	HashMap<String, String> walletInfoFromAPI = new HashMap<>();
	
  @Test
  public void executeDailyScript() throws IOException, InterruptedException, UnirestException {
	  
	  CreateReport.startTest("executeDailyScript");
	  
	  try {
		  System.out.println("hello");
		  
		  UserAccount.invokeDashboard(Util.getTestDataFromPropertiesFile("loginTestData", "loginEmail"), Util.getTestDataFromPropertiesFile("loginTestData", "loginPassword"), true);
		  
		  /*
		   * Click on Users in the menu
		   * In the Search box, enter that user email whose wallet balance is available
		   * Go to account details page of that user
		   */
		  
		  Web.getDriver().findElement(By.xpath(Util.getORValueFromPropertiesFile("userAccountLeftSideBarMenu", "userAccUsersMenuLink_xpath"))).click();
		  Thread.sleep(1000);
		  
		  
		  userRecordTable = Web.getDriver().findElement(By.id(Util.getORValueFromPropertiesFile("registeredAccountsPage", "userRecordTable_id")));
		  new WebDriverWait(Web.getDriver(), 30).until(ExpectedConditions.visibilityOfAllElements(userRecordTable.findElements(By.tagName(Util.getORValueFromPropertiesFile("registeredAccountsPage", "tableRow_tagName")))));
		  noOfUserRecords = userRecordTable.findElements(By.tagName(Util.getORValueFromPropertiesFile("registeredAccountsPage", "tableRow_tagName"))).size();
		  
		  if(noOfUserRecords>=1) {
			  
			  CreateReport.test.log(LogStatus.PASS, "Nisg Admin Portal: User record count checking", "Nisg Admin Portal: User records are displaying in Registered Accounts page");
			
			  Web.getDriver().findElement(By.xpath(Util.getORValueFromPropertiesFile("registeredAccountsPage", "searchUser_xpath"))).sendKeys(Util.getTestDataFromPropertiesFile("userAccTestData", "existingWalHolder_email"));
			  Web.getDriver().findElement(By.xpath(Util.getORValueFromPropertiesFile("registeredAccountsPage", "searchIcon_xpath"))).click();
			  Thread.sleep(1000);
			  
			  searchedUserTable = Web.getDriver().findElement(By.id(Util.getORValueFromPropertiesFile("registeredAccountsPage", "searchedUserTable_id")));
			  int searchedUserRecordCount = searchedUserTable.findElements(By.tagName(Util.getORValueFromPropertiesFile("registeredAccountsPage", "searchedRecordRow_tagname"))).size();
			  String searchedRecordEmail = Web.getDriver().findElement(By.xpath(Util.getORValueFromPropertiesFile("registeredAccountsPage", "searchedRecordEmail_xpath"))).getAttribute("innerHTML");
			  
			  
			  if(searchedUserRecordCount>=1 || searchedRecordEmail.equals(Util.getTestDataFromPropertiesFile("userAccTestData", "existingWalHolder_email"))) {
				  
				  CreateReport.test.log(LogStatus.PASS, "Nisg Admin Portal: Check searched user record", "Nisg Admin Portal: Searched user record is correct");
			 
				//Click on More button of the user			  
				  Web.getDriver().findElement(By.xpath(Util.getORValueFromPropertiesFile("registeredAccountsPage", "custTable_UserInfo_ColLeft_xpath") + Util.getORValueFromPropertiesFile("registeredAccountsPage", "custTable_User_MoreBtn_Right_xpath"))).click();
				  Thread.sleep(1000);
				  
				  expectedAccDetailsPageName = Web.getDriver().findElement(By.xpath(Util.getORValueFromPropertiesFile("registeredAccountsPage", "accDetails_PageHeading_xpath"))).getAttribute("innerHTML").trim();
				  actualAccDetailsPageName = Util.getTestDataFromPropertiesFile("userAccTestData", "AccDetails_PageName");
				  
				  if(expectedAccDetailsPageName.equals(actualAccDetailsPageName)) {
					 
					  CreateReport.test.log(LogStatus.PASS, "Nisg Admin Portal: Check Account Details page", "Nisg Admin Portal: User's Account Details page is opening");
					  
					  Web.explicitWait(Web.getDriver().findElement(By.xpath(Util.getORValueFromPropertiesFile("registeredAccountsPage", "walletInfoMenu_xpath"))));
					  Web.getDriver().findElement(By.xpath(Util.getORValueFromPropertiesFile("registeredAccountsPage", "walletInfoMenu_xpath"))).click();
					  Thread.sleep(1000);
					  
					  userWalletInfoTable = Web.getDriver().findElement(By.id(Util.getORValueFromPropertiesFile("registeredAccountsPage", "walletBalTable_id")));
					  noOfWalletInfo = userWalletInfoTable.findElements(By.tagName(Util.getORValueFromPropertiesFile("registeredAccountsPage", "walletInfoTableRow_tagName"))).size();
					 
					  for(int i=0; i<noOfWalletInfo; i++) {
						  
						  String walletCurrName = Web.getDriver().findElement(By.xpath(Util.getORValueFromPropertiesFile("registeredAccountsPage", "currInfo_RightXpath") + (i+1) + Util.getORValueFromPropertiesFile("registeredAccountsPage", "currName_LeftXpath"))).getAttribute("value");
						  double currSpecificWalletBal = Double.parseDouble(Web.getDriver().findElement(By.xpath(Util.getORValueFromPropertiesFile("registeredAccountsPage", "currInfo_RightXpath") + (i+1) + Util.getORValueFromPropertiesFile("registeredAccountsPage", "currBal_LeftXpath"))).getAttribute("innerHTML").replaceAll("[^0-9.]", ""));
						  displayedWalletInfo.put(walletCurrName, Double.toString(currSpecificWalletBal));
						  
						  HashMap<String, String> fetchWalletInfoFromAPI = getWalletBalAPIInfo();
						  
						  outer:
						  for(Map.Entry<String, String> walInfFrmAPI:fetchWalletInfoFromAPI.entrySet()) {
														
							 for(Map.Entry<String, String> walInfDspld:displayedWalletInfo.entrySet()) {							    
								 
								 if(walInfFrmAPI.getKey().equals(walInfDspld.getKey())) {										
									
									if(walInfFrmAPI.getValue().equals(walInfDspld.getValue())) {
										
										CreateReport.test.log(LogStatus.PASS, "Nisg Admin Portal: Check user wallet balance", "Nisg Admin Portal: User wallet balance is displaying properly. Displayed currency: " + walInfDspld.getKey() + ", CurrencyFromAPI: " + walInfFrmAPI.getKey() + ", Displayed Bal: " + walInfDspld.getValue() + ", BalanceFromAPI: " + walInfFrmAPI.getValue());
										break;
										
									}else {
										
										CreateReport.test.log(LogStatus.FAIL, "Nisg Admin Portal: Check user wallet balance", "Nisg Admin Portal: User wallet balance is not displaying properly. Displayed currency: " + walInfDspld.getKey() + ", CurrencyFromAPI: " + walInfFrmAPI.getKey() + ", Balance displayed: " + walInfDspld.getValue() + "BalanceFromAPI: " + walInfFrmAPI.getValue() + CreateReport.test.addScreenCapture(Web.takeScreenshot()));
										triggerEmail("Nisg Admin Portal: User wallet balance is not displaying properly. Displayed currency: " + walInfDspld.getKey() + ", CurrencyFromAPI: " + walInfFrmAPI.getKey() + ", Balance displayed: " + walInfDspld.getValue() + "BalanceFromAPI: " + walInfFrmAPI.getValue());
										break outer;
									}
									
								 }
							 }
						  }
						  
						  
					  }
					  
				  }else {
					  
					  System.out.println("Nisg Admin Portal: User's Account Details page is not opening");
					  CreateReport.test.log(LogStatus.FAIL, "Nisg Admin Portal: Check Account Details page", "Nisg Admin Portal: User's Account Details page is not opening" + CreateReport.test.addScreenCapture(Web.takeScreenshot()));
					  triggerEmail("Nisg Admin Portal: User's Account Details page is not opening");
				  }
			  
			  }else {
				  
				  CreateReport.test.log(LogStatus.FAIL, "Nisg Admin Portal: Check searched user record", "Nisg Admin Portal: Searched user record is not available" + CreateReport.test.addScreenCapture(Web.takeScreenshot()));
				  triggerEmail("Nisg Admin Portal: Searched user record is not available");
			  }			  
			  
			  
		  }else {
			  
			  System.out.println("Nisg Admin Portal: Please add atleast one user first in Registered Account page");
			  CreateReport.test.log(LogStatus.FAIL, "Nisg Admin Portal: User record count checking", "Nisg Admin Portal: No user record is available in Registered Accounts page" + CreateReport.test.addScreenCapture(Web.takeScreenshot()));
			  triggerEmail("Nisg Admin Portal: No user record is available in Registered Accounts page");
		  }
		  
		  
	  }catch(Exception e) {
		  
		  e.printStackTrace();
		  CreateReport.test.log(LogStatus.FATAL, e);
		  triggerEmail(e.toString());
		  
	  }finally {
		
		  Web.closeDriver();
	} 
	
  }
  
  public HashMap<String, String> getWalletBalAPIInfo() throws UnirestException, IOException{
	  
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
	  
	  Unirest.setTimeouts(0, 0);
	  HttpResponse<String> getWalletBalanceAPIResponse = Unirest.get(Util.getTestDataFromPropertiesFile("getWalletBalAPITestData", "getWalletBalAPI_UrlLeftPart") + Util.getTestDataFromPropertiesFile("getWalletBalAPITestData", "getWallBalAPIcustHashID") + "/" + Util.getTestDataFromPropertiesFile("getWalletBalAPITestData", "getWalBalAPIwalletHashID") + Util.getTestDataFromPropertiesFile("getWalletBalAPITestData", "getWalletBalAPI_UrlRightPart"))
			  .header("x-api-key", Util.getTestDataFromPropertiesFile("getWalletBalAPITestData", "getWalBalAPI-x-api-key"))
			  .header("clientHasID", Util.getTestDataFromPropertiesFile("getWalletBalAPITestData", "getWalBalAPI_clientHashID"))
			  .header("Authorization", "Bearer " + accessToken)
			  .asString();	  
	  
	  JSONArray getWalletBalJsonArray = new JSONArray(getWalletBalanceAPIResponse.getBody());
	  
	  
	  for(int i = 0; i<getWalletBalJsonArray.length(); i++) {
		  
		  String currSymbol = getWalletBalJsonArray.getJSONObject(i).getString("curSymbol");		  
		  double balance = getWalletBalJsonArray.getJSONObject(i).getDouble("balance");		  
		  
		  walletInfoFromAPI.put(currSymbol, Double.toString(balance));
	  }	  

	  
	return walletInfoFromAPI; 
	  
  }
  
  public void triggerEmail(String message) throws UnirestException, IOException {
	  
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
