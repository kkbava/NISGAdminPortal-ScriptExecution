package com.stylopay.genericUtility;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class Web{
	
	private static WebDriver driver;	

	private WebDriver invokeChrome() throws IOException {
		
		System.setProperty("webdriver.chrome.driver", "./externalFiles/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get(Util.getEnvDetailsProperties().getProperty("url"));
		driver.manage().window().maximize();
		
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.manage().deleteAllCookies();
		
		return driver;		
	}

	public static WebDriver getDriver() {
		return driver;
	}

	public static void setDriver(String browser) throws IOException {
		
		if(browser.equals("chrome")) {			
			driver = new Web().invokeChrome();
		}
		
	}
	
	public static void explicitWait(WebElement element) {
		
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOf(element));
	}
	
	public static String takeScreenshot() throws IOException {
		
		 String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		 TakesScreenshot ts = (TakesScreenshot) driver;
		 File source = ts.getScreenshotAs(OutputType.FILE);
		                
		 String destination = System.getProperty("user.dir") + "/report/screenshot/"+ dateName+".png";
		 File finalDestination = new File(destination);
		 FileUtils.copyFile(source, finalDestination);
		 return destination;

	}
	
	public static void closeDriver() {
		
		driver.quit();
	}

}
