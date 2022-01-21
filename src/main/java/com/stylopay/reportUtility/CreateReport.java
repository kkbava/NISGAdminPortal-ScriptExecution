package com.stylopay.reportUtility;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class CreateReport {

	static ExtentReports report;
	public static ExtentTest test;
	
	
	public static void initialiseReport(){
		
		report = new ExtentReports(System.getProperty("user.dir")+"//report//ExtentReportResults.html");
		
	}
	
	public static void startTest(String tcName) {
		test = report.startTest(tcName);
	}
	
	
	
	public static void endTest()
	{
	report.flush();
	}
}
