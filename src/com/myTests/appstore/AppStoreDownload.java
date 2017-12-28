package com.myTests.appstore;

import org.testng.annotations.Test;

public class AppStoreDownload extends AppStoreTest {
	@Test(groups = {"AppStore"})
	public void test() {
		String OS = client.getDeviceProperty("os.device");
		client.deviceAction("HOME");
		client.click("default", "Store", 0, 1);
		if(OS.equals("ANDROID")) {
			if(client.isElementFound("default", "search_button")) {
			 client.click("default","search_button",0,1);
			 client.sendText("FlashLight");
			 client.sendText("{ENTER}");
		
			}else {
			client.elementSendText("default", "Searchstore", 0, "FlashLight");
			}
			 client.waitForElement("default", "play_card", 0, 30000);
			 client.click("default", "play_card", 0, 1);

		}
		else if(OS.equals("IOS_APP")) {
			 client.click("default","search_button",0,1);
			 client.click("default", "Searchstore", 0, 1);
			 client.sendText("FlashLight");
			 client.sendText("{ENTER}");
			 client.click("default", "IOSCARD2", 0, 1);
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
