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

public class NativeTest2 extends DemoTest{
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

	

	@AfterMethod(groups = {"native2"})
	public void tearDown() {
		client.stopLoggingDevice();

			client.generateReport(true);
		}

	}
