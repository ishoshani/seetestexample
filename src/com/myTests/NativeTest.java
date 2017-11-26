package com.myTests;


//package <set your test package>;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.annotations.BeforeMethod;

import com.experitest.client.Client;
import com.experitest.client.GridClient;
/**
 *
 */
public class NativeTest{
	private String host = "localhost";
	private int port = 8889;
	private String projectBaseDirectory = "C:\\Users\\ido.shoshani\\workspace\\pExperitestDemo";
	private String installPath = "";
	protected Client client = null;
	protected String scenario;
	protected String expected;
	protected String actual;
	private String runtime;
	private String device;
	private String OS;
	
	
	@Parameters({"isGrid"})
	@BeforeMethod(groups = {"native1"})
	public void setUp(String isGrid){
		Boolean createGrid = Boolean.parseBoolean(isGrid);
		if(createGrid) {
			  GridClient gridClient = new GridClient("ido","Espeon123", "", "https://stage.experitest.com:443");
		      client = gridClient.lockDeviceForExecution("native1", "", 120, TimeUnit.MINUTES.toMillis(2));

		}else{
		client = new DemoClient(host, port, true,"EriBank", runtime);
		device = client.waitForDevice("", 30000);
		client.setDevice(device);
		}
		client.setProjectBaseDirectory(projectBaseDirectory);
		client.setReporter("xml", "reports", "NativeTest1");
	
		OS = client.getDeviceProperty("device.os");
		client.setSpeed("Fast");
		if(OS.equals("IOS_APP")) {
			client.install("cloud:com.experitest.ExperiBank", true, false);
			client.launch("com.experitest.ExperiBank", true, true);      

		}else if(OS.equals("ANDROID")) {
			client.install("cloud:com.experitest.ExperiBank/.LoginActivity", true, false);
			client.launch("com.experitest.ExperiBank/.LoginActivity", true, false);


		}
		
	}


	@Test(groups = {"native1"})
	public void testNativeLogin() {
		
		File f = new File("testLogin.csv");
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
				client.elementSendText("default", "usernameTextField", 0, user);
				client.elementSendText("default", "passwordTextField", 0, password);
				client.click("default", "Login", 0, 1);
				if(client.isElementFound("default", "Close")){
					client.click("default", "Close", 0, 1);
				}
				boolean clientSuccess = client.isElementFound("default", "Make Payment");
				if(user.equals("company") && password.equals("company")) {
					Assert.assertEquals(true, clientSuccess);
				}else {
					Assert.assertEquals(false, clientSuccess);
				}
			}}
		catch(IOException e) {
			Assert.fail("failed with error"+e);
		}finally {
			r.close();
		}

		}
	
		@Test(groups = {"native1"})
		
		public void OrderTest() {
		client.elementSendText("default", "usernameTextField", 0, "company");
		client.elementSendText("default", "passwordTextField", 0, "company");
		client.click("default", "Login", 0, 1);
		double start = 100;
		double spent = 66;

		String startString = client.hybridRunJavascript("", 0, "var result = document.getElementsByTagName(\"H1\")[0].innerText;");
		startString = startString.replaceAll("[^0-9]", "");
		System.out.println(startString);
		start = Double.parseDouble(startString)/100;
		System.out.print(start);
		client.click("default", "Make Payment", 0, 1);

		if(client.waitForElement("default", "Name", 0, 30000)){
			// If statement
		}
		client.elementSendText("default", "Phone", 0, "4448416666");
		client.elementSendText("default", "Name", 0, "test");
		client.elementSendText("default", "Amount", 0, "66");
		client.click("default", "Select_1", 0, 1);
		client.click("default", "USA_1", 0, 1);
		client.click("default", "Send Payment_1", 0, 1);
		client.click("default", "Yes_1", 0, 1);
		String actualString = client.hybridRunJavascript("", 0, "var result = document.getElementsByTagName(\"H1\")[0].innerText;");
		actualString = actualString.replace("\n", "");
		String expectedString = String.format("Your balance is: %.2f$",(start-spent));
		Assert.assertEquals(expectedString, actualString);
	}

	@AfterMethod(groups = {"native1"})
	public void tearDown() {
		client.generateReport(false);
		client.releaseClient();
		
		}
		
		
	
		
	}


