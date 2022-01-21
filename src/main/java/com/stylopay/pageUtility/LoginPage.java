package com.stylopay.pageUtility;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.stylopay.genericUtility.Util;
import com.stylopay.genericUtility.Web;

public class LoginPage {
	
	public static void doLogin(String username, String password, boolean submitLogin) throws IOException, InterruptedException {
		
		try {
		
			Web.setDriver(Util.getEnvDetailsProperties().getProperty("browser"));
			WebDriver driver = Web.getDriver();
			
			driver.findElement(By.id(Util.getORValueFromPropertiesFile("loginPage", "email_id"))).sendKeys(username);
			driver.findElement(By.name(Util.getORValueFromPropertiesFile("loginPage", "password_name"))).sendKeys(password);
			
			if(submitLogin==true) {
				
				Web.explicitWait(driver.findElement(By.xpath(Util.getORValueFromPropertiesFile("loginPage", "loginBtn_xpath"))));
				driver.findElement(By.xpath(Util.getORValueFromPropertiesFile("loginPage", "loginBtn_xpath"))).click();
				Thread.sleep(1000);
			}
			
		}catch(Exception e) {
			
			e.printStackTrace();
		}
	}

}
