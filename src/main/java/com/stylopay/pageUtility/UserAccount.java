package com.stylopay.pageUtility;

import com.stylopay.genericUtility.Util;

public class UserAccount {
	
	public static void invokeDashboard(String username, String password, boolean submitLogin) {
		
		try {
			
			LoginPage.doLogin(username, password, submitLogin);
			Thread.sleep(1000);
			
		}catch(Exception e) {
			
			e.printStackTrace();
		}
	}

}
