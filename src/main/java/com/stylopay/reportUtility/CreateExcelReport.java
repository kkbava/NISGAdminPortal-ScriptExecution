package com.stylopay.reportUtility;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.mail.EmailException;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class CreateExcelReport {
	
	static String fileName;
	static Workbook testReport;
	static Sheet reportSheet;
	static HSSFRow rowHead;
	static FileOutputStream fileOut;
	
	public static void main(String args[]) throws IOException, EmailException {
		
		String fileName = "./Report/TestExcelReport.xls";
		
		testReport = new HSSFWorkbook();
		
		reportSheet = testReport.createSheet("Report Details");
		rowHead = (HSSFRow) reportSheet.createRow(0);	
		
		rowHead.createCell(0).setCellValue("Testcase Name");
		rowHead.createCell(1).setCellValue("Test Execution Result");
		rowHead.createCell(2).setCellValue("Test Execution Status");
		rowHead.createCell(3).setCellValue("Error details");
		
		generateTestReport((short)1, "TC1", "TC1 Passed", "PASS", "");
		generateTestReport((short)2, "TC2", "TC2 Failed", "FAIL", "IO Exception");
		
		fileOut = new FileOutputStream(fileName);
		testReport.write(fileOut);
		fileOut.close();
		testReport.close();
		
		System.out.println("Test excel report has been generated");
		
		
	}
	
	public static void generateTestReport(short rowCount, String tcName, String executionRes, String status, String err) throws IOException {
					
		
		HSSFRow row = (HSSFRow) reportSheet.createRow(rowCount);
		row.createCell(0).setCellValue(tcName);
		row.createCell(1).setCellValue(executionRes);
		row.createCell(2).setCellValue(status);
		row.createCell(3).setCellValue(err);		
		
	}
	

}
