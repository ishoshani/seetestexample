package com.myTests;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.experitest.auto.BaseTest;
import com.experitest.client.Client;
import com.experitest.client.InternalException;

public class NativeTest2 extends RunnableTest {
	private String host = "localhost";
	private int port = 8889;
	private String projectBaseDirectory = "C:\\Users\\ido.shoshani\\workspace\\pExperitestDemo";
	protected DemoClient client = null;
	private String installPath= "";
	private String runtime;
	private String device;
	private String OS;
	public NativeTest2(String runtime){
		this.runtime = runtime;
	}
	
	@Override
	void theTests() throws AssertionError {
		test();

	}
	@Before
	public void setUp() throws Exception {
		client = new DemoClient(host, port, true, runtime);
		client.setProjectBaseDirectory(projectBaseDirectory);
		device = client.waitForDevice("", 30000);
		testName = "applicationTest";
		client.setDevice(device);
		OS = client.getDeviceProperty("device.os");
		client.setData(device, "ExperitestDemo", this.getClass(), installPath);
		client.setLocation("-122.445848", "37.76033");
		client.startLoggingDevice(client.destinationLocation);
	}

	@Test
	public void test() {
		
		if(OS.equals("IOS_APP")) {
			client.install("ExperiDemo", true, false);
			client.launch("ExperiDemo", true, true);      

		}else if(OS.equals("ANDROID")) {
			client.install("com.example.isho.experitestdemo/.Login", true, false);
			client.launch("com.example.isho.experitestdemo/.Login", true, false);
		}
		client.getCurrentApplicationName();
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
					client.AssertBoolean(getStatus(),true, clientSuccess);
					if(OS.equals("ANDROID")) {
						client.deviceAction("Back");				
					}else {
						client.click("default", "Exit", 0, 1);
					}
				}else {
					client.AssertBoolean(getStatus(),false, clientSuccess);
				}
			}}
		catch(IOException e) {
			Assert.fail("failed with error"+e);
		}finally {
			r.close();
		}
		System.out.println("got through first part of the test");
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
			client.AssertIntsEqual(getStatus(),expectedScore, Integer.parseInt(client.elementGetProperty("default", "counter", 0, "text")));
			client.click("default", "Again", 0, 1);

		}



	}
	@After
	public void tearDown() {
		switch (getStatus()) {
		case 0:
			client.generateReport(true);
			client.stopLoggingDevice();
			break;
		case -1:
			System.err.println("failed the first time");
			client.generateReport(false);
			TestSuite();
			break;
		case -2:
			System.err.println("failed the second time rebooting");
			client.generateReport(false);
			try {
				client.reboot(120000);
				client.deviceAction("Unlock");
				}catch(InternalException e) {
					decStatus();
				}
			TestSuite();
			break;
		default:
			client.generateReport(true);
			client.stopLoggingDevice();
			break;
		}

	}
}
