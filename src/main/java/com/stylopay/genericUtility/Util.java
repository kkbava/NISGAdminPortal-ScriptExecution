package com.stylopay.genericUtility;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Util {
	
	//private static Properties properties;
	
	public static Properties getEnvDetailsProperties() throws IOException {
		
		Properties properties = new Properties();
		FileInputStream inputStream = new FileInputStream("./config/envDetails.properties");
		properties.load(inputStream);
		
		return properties;		
	}
	
	public static String getORValueFromPropertiesFile(String pagename, String key) throws IOException {
		
		Properties properties = new Properties();
		String keyValue="";
		
		FileInputStream inputStream = new FileInputStream("./objectRepository/" + pagename + ".properties");
		properties.load(inputStream);
		
		try {
			
			keyValue = properties.getProperty(key);	
			
		}catch(Exception e) {
			
			
		}
		
		return keyValue;	
	}
	
	public static String getTestDataFromPropertiesFile(String testDataPageName, String key) throws IOException {
		
		Properties properties = new Properties();
		String keyValue = "";	
		
		FileInputStream inputstream = new FileInputStream("./testData/" + testDataPageName + ".properties");		
		properties.load(inputstream);
		
		try {
			
			keyValue = properties.getProperty(key);
			
		}catch(Exception e) {
			
			e.printStackTrace();
		}
		
		return keyValue;
	}

}
