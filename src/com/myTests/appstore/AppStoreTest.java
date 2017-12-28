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
		testName = "AppStore Test";
	}
	/*public AppStoreTest(String runtime) {
		this.runtime = runtime;
	}*/
	/*@Parameters("isGrid")
	@BeforeMethod(groups = {"AppStore"})
	public void setUp(String isGrid) throws Exception {
		Boolean createGrid = Boolean.parseBoolean(isGrid);
		Client tempClient;
		if(createGrid) {
			  GridClient gridClient = new GridClient("ido","Espeon123", "", "https://stage.experitest.com:443");
		      tempClient = gridClient.lockDeviceForExecution("AppStore", "", 120, TimeUnit.MINUTES.toMillis(2));

		}else{
	
			tempClient = new Client(host, port, true);
			device = tempClient.waitForDevice("", 30000);
			tempClient.setDevice(device);
		}	      
		client = new DemoClient(tempClient, "AppStore", runtime);
		client.setProjectBaseDirectory(projectBaseDirectory);
	     
		  OS = client.getDeviceProperty("device.os");
	      client.openDevice();
	}

	*/
	
	
	@AfterMethod(groups = {"AppStore"})
	public void tearDown() {
			client.generateReport(false);
			client.getDeviceLog();
			client.releaseClient();
		}

	}

