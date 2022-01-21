package com.stylopay.driver;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import com.stylopay.reportUtility.CreateReport;

public class ManageTestSuites {
	
	@BeforeSuite(alwaysRun = true)
	public static void beforeSuite() {
		
		CreateReport.initialiseReport();		
	}
	
	@AfterSuite(alwaysRun = true)
	public static void afterSuite() {
		
		CreateReport.endTest();
		
	}

}
