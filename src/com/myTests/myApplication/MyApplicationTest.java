package com.myTests.myApplication;


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
import com.myTests.DemoClient;
import com.myTests.DemoTest;

public class MyApplicationTest extends DemoTest{
	private String host = "localhost";
	private int port = 8889;
	private String projectBaseDirectory = "C:\\Users\\ido.shoshani\\workspace\\pExperitestDemo";
	private String installPath= "";
	private String runtime;
	private String device;
	private String OS;
	
	public MyApplicationTest() {
		super();
		testName="MyApplication";
	}
	
	@Parameters("isGrid")
	@BeforeMethod()
	public void setUp(String isGrid) throws Exception {

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

	

	@AfterMethod()
	public void tearDown() {
		client.stopLoggingDevice();

			client.generateReport(true);
		}

	}
