package com.myTests.eribank;


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
import com.myTests.DemoClient;
import com.myTests.DemoTest;
/**
 *
 */
public class NativeTest extends DemoTest{
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
	
	
	@Parameters({"isGrid"})
	@BeforeMethod(groups = {"native1"})
	public void setUp(String isGrid){
		Boolean createGrid = Boolean.parseBoolean(isGrid);
		Client tempClient;
		if(createGrid) {
			  GridClient gridClient = new GridClient("ido","Espeon123", "", "https://stage.experitest.com:443");
		      tempClient = gridClient.lockDeviceForExecution("native1", "", 120, TimeUnit.MINUTES.toMillis(2));
		}else{	
			tempClient = new Client(host, port, true);
			String device = tempClient.waitForDevice("", 30000);
			tempClient.setDevice(device);
		}
		client = new DemoClient(tempClient,"EriBank",runtime);
	
		client.setProjectBaseDirectory(projectBaseDirectory);	
		OS = client.getDeviceProperty("device.os");
		client.setSpeed("Slow");
		if(OS.equals("IOS_APP")) {
			client.install("cloud:com.experitest.ExperiBank", true, false);
			client.launch("cloud:com.experitest.ExperiBank", true, true);      

		}else if(OS.equals("ANDROID")) {
			client.install("cloud:com.experitest.ExperiBank/.LoginActivity", true, false);
			client.launch("cloud:com.experitest.ExperiBank/.LoginActivity", true, false);


		}
		client.openDevice();
		
	}



		

	@AfterMethod(groups = {"native1"})
	public void tearDown() {
		client.generateReport(false);
		client.releaseClient();
		
		}
		
		
	
		
	}


