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
	protected String scenario;
	protected String expected;
	protected String actual;
	private String runtime;
	private String device;
	
	public NativeTest() {
		super();
		testName="NativeTest";
	}
	@Parameters({"isGrid"})
	@BeforeMethod(groups = {"native1"})
	public void setUp(String isGrid) throws Exception{
	
		client.setSpeed("Fast");
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


