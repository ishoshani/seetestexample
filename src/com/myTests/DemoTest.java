package com.myTests;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import java.io.File;
import java.util.Properties;

import org.testng.Assert;
import org.testng.TestRunner;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.experitest.client.Client;
import com.experitest.client.GridClient;

public abstract class DemoTest {
	protected static String runtime;
	public String deviceQ = "";
	public String testName = "DemoTest";
	protected Properties cloudProperties = new Properties();
	private String host = "localhost";
	private int port = 8889;
	private String projectBaseDirectory = "pExperitestDemo";
	protected DemoClient client = null;
	private String device;
	private String OS;
	
	@BeforeSuite(alwaysRun=true)
	public void setRuntime() {
		runtime = String.valueOf(System.currentTimeMillis());
	}
	public static String getRunTime() {
		return runtime;
	}

	@Parameters("isGrid")
	@BeforeMethod()
	public void setUp(String isGrid) throws Exception {
		Boolean createGrid = Boolean.parseBoolean(isGrid);
		Client tempClient;
		if(createGrid) {
			  GridClient gridClient = new GridClient("ido","Espeon123",deviceQ, "https://sales.experitest.com:443");
		      tempClient = gridClient.lockDeviceForExecution(testName, "", 120, TimeUnit.MINUTES.toMillis(2));

		}else{
	
			tempClient = new Client(host, port, true);
			device = tempClient.waitForDevice(deviceQ, 30000);
			tempClient.setDevice(device);
		}	      
		client = new DemoClient(tempClient, testName, runtime);
		client.setProjectBaseDirectory(projectBaseDirectory);
	     
		  OS = client.getDeviceProperty("device.os");
	      client.openDevice();
	}
	
	
	
	private void initCloudProperties() throws FileNotFoundException, IOException {
		FileReader fr = new FileReader("cloud.properties");
		cloudProperties.load(fr);
		fr.close();
	}
	@Test
	public void nothingTest() {
		Assert.assertEquals(2+2, 4);
	}

	@AfterMethod(groups = {"AppStore"})
	public void tearDown() {
			client.generateReport(false);
			client.getDeviceLog();
			client.releaseClient();
		}
	

}
