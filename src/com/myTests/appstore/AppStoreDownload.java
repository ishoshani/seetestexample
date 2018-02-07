package com.myTests.appstore;

import org.testng.Assert;
import org.testng.annotations.Test;

public class AppStoreDownload extends AppStoreTest {
	@Test(groups = {"AppStore"})
	public void test() {
		System.out.println(OS);
		if(!OS.equals("ANDROID")&&!OS.equals("IOS_APP")) {
			Assert.fail("Input wrong OS:"+OS);
		}
		client.setThrowExceptionOnFail(false);
		client.deviceAction("HOME");
		client.applicationClearData("com.google.android.play");

		client.click("default", "Store", 0, 1);
		client.sendText("{ENTER}");
		if(OS.equals("ANDROID")) {
			if(client.isElementFound("default", "SearchOut")) {
			 client.click("default","SearchOut",0,1);
			}else {
			client.click("default", "search_button", 0, 1);
			}
			 client.elementSendText("default", "Searchstore", 0, "FlashLight");
			 client.sendText("{ENTER}");
			 client.waitForElement("default", "play_card", 0, 30000);
			 client.click("default", "play_card", 0, 1);

		}
		else if(OS.equals("IOS_APP")) {
			 client.click("default","search_button",0,1);
			 client.click("default", "Searchstore", 0, 1);
			 client.sendText("Flashlight");
			 client.sendText("{ENTER}");
			 client.sync(10, 0, 30000);
			 if(client.isElementFound("Native", "xpath=//*[@text='GET']")) {
			 client.click("NATIVE","xpath=//*[@text='GET']",0,1);
			 }
		}
		
		if(client.isElementFound("default", "INSTALL")) {
			client.click("default", "INSTALL", 0, 1);
			client.waitForElementToVanish("default", "Install", 0, 30000);
		}
		
		client.isElementFound("default", "OPEN", 0);
		client.sync(10, 0, 30000);
		client.click("default", "OPEN", 0, 1);
		String appname = client.getCurrentApplicationName();
		client.applicationClose(appname);
		client.uninstall(appname);
	}
}
