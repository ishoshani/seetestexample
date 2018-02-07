package com.myTests.appstore;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.*;

import org.testng.asserts.*;

import com.experitest.client.Client;
import com.experitest.client.GridClient;
import com.myTests.DemoClient;
import com.myTests.DemoTest;

public class AppStoreTest extends DemoTest{
	
	public AppStoreTest() {
		super();
		testName="AppStore Test";
	}

	@Parameters("isGrid")
	@BeforeMethod(groups = {"AppStore"})
	public void setUp(String isGrid) throws Exception {

	    client.openDevice();
	}

	
	
	
	@AfterMethod(groups = {"AppStore"})
	public void tearDown() {
			client.generateReport(false);
			client.getDeviceLog();
			client.releaseClient();
		}

	}

