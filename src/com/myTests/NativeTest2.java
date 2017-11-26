package com.myTests;


import static org.junit.Assert.assertEquals;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import org.testng.annotations.*;

import com.experitest.client.InternalException;

public class NativeTest2 {
	private String host = "localhost";
	private int port = 8889;
	private String projectBaseDirectory = "C:\\Users\\ido.shoshani\\workspace\\pExperitestDemo";
	protected DemoClient client = null;
	private String installPath= "";
	private String runtime;
	private String device;
	private String OS;
	

	@BeforeMethod(groups= {"native2"})
	public void setUp() throws Exception {
		client = new DemoClient(host, port, true, runtime);
		client.setProjectBaseDirectory(projectBaseDirectory);
		device = client.waitForDevice("", 30000);
		client.setDevice(device);
		OS = client.getDeviceProperty("device.os");
		client.setData(device, "ExperitestDemo", this.getClass(), installPath);
		client.setLocation("-122.445848", "37.76033");
		client.startLoggingDevice(client.destinationLocation);
		if(OS.equals("IOS_APP")) {
			client.install("ExperiDemo", true, false);
			client.launch("ExperiDemo", true, true);      

		}else if(OS.equals("ANDROID")) {
			client.install("com.example.isho.experitestdemo/.Login", true, false);
			client.launch("com.example.isho.experitestdemo/.Login", true, false);
		}
		client.getCurrentApplicationName();
	}

	@Test(groups = {"native2"})
	public void test() {
		
		
		if(client.getNetworkConnection("WIFI")) {
			client.capture();
		}
		File f = new File("testLogin2.csv");
		Scanner r = null;
		try {
			r = new Scanner(f);
			r.useDelimiter("\n");
			r.next();
			while(r.hasNext()) {
				String line = r.next();
				String[] tokens = line.split(",");
				String user = tokens[0];
				String password = tokens[1];
				client.elementSendText("default", "topLoginField", 0, user);
				client.elementSendText("default", "bottomLoginField", 0, password);
				client.sendText("{ENTER}");
				client.click("default", "Login", 0, 1);
				boolean clientSuccess = client.isElementFound("default", "Game");
				if(user.equals(password)&&!user.isEmpty()) {
					assertEquals(true, clientSuccess);
					if(OS.equals("ANDROID")) {
						client.deviceAction("Back");				
					}else {
						client.click("default", "Exit", 0, 1);
					}
				}else {
					assertEquals(false,clientSuccess);
				}
			}}
		catch(IOException e) {
			fail("failed with error"+e);
		}finally {
			r.close();
		}
		System.out.println("got through first part of the test");
	}
		
		
		@Test(groups = {"native2"})
		public void test2() {
		client.elementSendText("default", "topLoginField", 0, "login");
		client.elementSendText("default", "bottomLoginField", 0, "login");
		client.sendText("{ENTER}");
		client.click("default", "Login", 0, 1);
		client.click("default", "Game", 0, 1);
		for(int j = 0; j < 3; j++) {

			int expectedScore = new Random().nextInt(5);
			int turns  = expectedScore;
			client.waitForElement("default", "clicker_button", 0, 3000);
			for(int i =0; i<turns;i++) {
				playgame:
					try {
						client.click("default", "clicker_button", 0, 1);
					}catch (InternalException e) {
						System.out.println("too slow, lost at"+i);
						expectedScore = i;
						break playgame;
					}
			}
			assertEquals(expectedScore,Integer.parseInt(client.elementGetProperty("default", "counter", 0, "text")));
			client.click("default", "Again", 0, 1);

		}



	}
	@AfterMethod(groups = {"native2"})
	public void tearDown() {
		
			client.generateReport(true);
			client.stopLoggingDevice();
		}

	}