package com.myTests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.*;

import org.testng.asserts.*;

import com.experitest.client.Client;
import com.experitest.client.GridClient;

public class AppStoreTest{
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
	@Parameters("isGrid")
	@BeforeMethod(groups = {"AppStore"})
	public void setUp(String isGrid) throws Exception {
		Boolean createGrid = Boolean.parseBoolean(isGrid);
		Client tempClient;
		if(createGrid) {
			  GridClient gridClient = new GridClient("ido","Espeon123", "", "https://stage.experitest.com:443");
		      tempClient = gridClient.lockDeviceForExecution("AppStore", "", 120, TimeUnit.MINUTES.toMillis(2));

		}else{
	
			tempClient = new Client(host, port, true);
			device = tempClient.waitForDevice("", 30000);
			tempClient.setDevice(device);
		}	      
		client = new DemoClient(tempClient, "AppStore", runtime);
		client.setProjectBaseDirectory(projectBaseDirectory);
	     
		  OS = client.getDeviceProperty("device.os");
	      client.openDevice();
	}

	@Test(groups = {"AppStore"})
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
		
		client.isElementFound("default", "OPEN", 0);
		client.sync(10, 0, 30000);
		client.click("default", "OPEN", 0, 1);
		String appname = client.getCurrentApplicationName();
		client.applicationClose(appname);
		client.uninstall(appname);
	}
	@Test(groups = {"Appstore"})
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
				String pathToProof = client.capture();
				client.report(pathToProof,String.format("$d : %s", i,Titles[i]), true);
			}

		}
		
		
	}
	
	
	@AfterMethod(groups = {"AppStore"})
	public void tearDown() {
			client.generateReport(true);
			client.getDeviceLog();
			client.releaseClient();
		}

	}

