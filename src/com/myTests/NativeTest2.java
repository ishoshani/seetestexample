package com.myTests;


import static org.junit.Assert.assertEquals;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.testng.annotations.*;

import com.experitest.client.Client;
import com.experitest.client.GridClient;
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
	
	@Parameters("isGrid")
	@BeforeMethod(groups= {"native2"})
	public void setUp(String isGrid) throws Exception {
		Boolean createGrid = Boolean.parseBoolean(isGrid);
		Client tempClient;
		if(createGrid) {
			  GridClient gridClient = new GridClient("ido","Espeon123", "", "https://stage.experitest.com:443");
		      tempClient = gridClient.lockDeviceForExecution("native2", "", 120, TimeUnit.MINUTES.toMillis(2));

		}else{
		tempClient = new Client(host, port, true);
		device = tempClient.waitForDevice("", 30000);
		tempClient.setDevice(device);
		
		}
		client = new DemoClient(tempClient, "ExperiDemo", runtime);
		client.setProjectBaseDirectory(projectBaseDirectory);
	OS = client.getDeviceProperty("device.os");
		client.setLocation("37.76033", "-122.445848");
		if(OS.equals("IOS_APP")) {
			client.install("cloud:ExperiDemo", true, false);
			client.launch("ExperiDemo", true, true);      

		}else if(OS.equals("ANDROID")) {
			client.install("cloud:com.example.isho.experitestdemo/.Login", true, false);
			client.launch("com.example.isho.experitestdemo/.Login", true, false);
		}
		client.getCurrentApplicationName();
	}

	@Test(groups = {"native2"})
	public void test() {
		
		
	
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
