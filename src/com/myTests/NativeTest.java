package com.myTests;

import com.experitest.auto.BaseTest;

//package <set your test package>;
import com.experitest.client.*;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.junit.*;
/**
 *
 */
public class NativeTest extends RunnableTest {
	private String host = "localhost";
	private int port = 8889;
	private String projectBaseDirectory = "C:\\Users\\ido.shoshani\\workspace\\pExperitestDemo";
	private String installPath = "";
	protected DemoClient client = null;
	protected String scenario;
	protected String expected;
	protected String actual;
	private String runtime;
	private String device;
	private String OS;
	@Override
	public void theTests() {
		testNativeLogin();
	}
	public NativeTest() {
		super();
		this.runtime= String.valueOf(System.currentTimeMillis());
	}
	public NativeTest(String runtime) {
		super();
		this.runtime = runtime;
	}
	
	@Before
	public void setUp(){
		client = new DemoClient(host, port, true, runtime);
		client.setProjectBaseDirectory(projectBaseDirectory);
		client.setReporter("xml", "reports", "NativeTest1");
		testName = "NativeTest1";
		device = client.waitForDevice("", 30000);
		client.setDevice(device);
		OS = client.getDeviceProperty("device.os");
		client.setData(device, "Eribank", this.getClass(),installPath);
		client.setSpeed("Fast");
		
	}


	@Test
	public void testNativeLogin() {
		if(OS.equals("IOS_APP")) {
			client.install("com.experitest.ExperiBank", true, false);
			client.launch("com.experitest.ExperiBank", true, true);      

		}else if(OS.equals("ANDROID")) {
			client.install("com.experitest.ExperiBank/.LoginActivity", true, false);
			client.launch("com.experitest.ExperiBank/.LoginActivity", true, false);


		}
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
					client.AssertBoolean(getStatus(), true, clientSuccess);
				}else {
					client.AssertBoolean(getStatus(), false, clientSuccess);
				}
			}}
		catch(IOException e) {
			Assert.fail("failed with error"+e);
		}finally {
			r.close();
		}



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
		client.AssertStringsEqual(getStatus(), expectedString, actualString);
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
			break;
		}
		
		
	
		
	}
}

