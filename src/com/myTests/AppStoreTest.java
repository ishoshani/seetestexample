package com.myTests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.experitest.auto.BaseTest;
import com.experitest.client.Client;
import com.experitest.client.InternalException;

import junit.framework.Assert;

public class AppStoreTest extends RunnableTest {
	private String host = "localhost";
	private int port = 8889;
	private String installPath = "";
	private String projectBaseDirectory = "C:\\Users\\ido.shoshani\\workspace\\pExperitestDemo";
	protected DemoClient client = null;
	private String device;
	private String runtime;
	private String OS;
	/*public AppStoreTest(String runtime) {
		this.runtime = runtime;
	}*/
	@Before
	public void setUp() throws Exception {
		  client = new DemoClient(host, port, true, runtime);
	      client.setProjectBaseDirectory(projectBaseDirectory);
	      device = client.waitForDevice("", 30000);
	      testName = "AppStore Test";
		  client.setDevice(device);
		  OS = client.getDeviceProperty("device.os");
	      client.setData(device	, "appstore", this.getClass(), installPath);
	      client.openDevice();
	}
	@Override
	void theTests() throws AssertionError {
		test();
		test2();
	}
	@Test
	public void test() {
		
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
		
		client.isElementFoundTest(getStatus(), "default", "OPEN", 0);
		client.sync(10, 0, 30000);
		client.click("default", "OPEN", 0, 1);
		String appname = client.getCurrentApplicationName();
		client.applicationClose(appname);
		client.uninstall(appname);
	}
	@Test
	public void test2() {
		client.deviceAction("HOME");
		client.click("default", "Store", 0, 1);
		client.click("default", "TOP CHARTS", 0, 1);
		if(OS.equals("ANDROID")) {
		client.click("default", "TOP CHARTS", 0, 1);
		client.click("default","toggle_switch_button", 0, 1);
		String[] Titles = new String[10];
		for(int i = 0; i<10; i++) {
				Titles[i] = client.elementGetProperty("NATIVE","xpath=//*[@id='play_card' and @width>0 and ./*[@text='"+(1+i)+"']]//*[@id='li_title']",0,"text");
				if(i%3 == 0) {
					client.swipe("down", 1250, 20);
				}
				System.out.println(Titles[i]);
			}
		}else {
			client.click("default", "Free", 0, 1);
			String[] Titles = new String[10];
			for(int i = 0; i<10; i++) {
				String currentText = client.elementGetProperty("NATIVE", "xpath=//*[starts-with(@text,'"+(i+1)+",')]", 0, "text");
				String[] textTokens = currentText.split(", ");
				Titles[i]=textTokens[1];
				if(i%3 == 0) {
					client.swipe("down", 1250, 20);
				}
				System.out.println(Titles[i]);
				client.capture();
			}

		}
		
		
	}
	
	
	@After
	public void tearDown() {
		switch (getStatus()) {
		case 0:
			client.generateReport(true);
			break;
		case -1:
			System.err.println("failed the first time");
			client.generateReport(false);
			client.getDeviceLog();
			TestSuite();
			break;
		case -2:
			System.err.println("failed the second time rebooting");
			client.generateReport(false);
			client.getDeviceLog();
			try {
				client.reboot(120000);
				client.deviceAction("Unlock");
				}catch(InternalException e) {
					decStatus();
					client.getDeviceLog();
				}
			TestSuite();
			break;
		default:
			client.generateReport(true);
			client.getDeviceLog();
			break;
		}

	}

}
