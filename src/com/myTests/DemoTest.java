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
import org.testng.annotations.Test;

import com.experitest.client.Client;
import com.experitest.client.GridClient;

public class DemoTest {
	protected static String runtime;
	public String deviceQ = "";
	public String testName;
	protected Properties cloudProperties;
	private String host = "localhost";
	private int port = 8889;
	private String projectBaseDirectory = "pExperitestDemo";
	protected DemoClient client = null;
	private String device;
	protected String OS;
	private String accessKey;
	
	@BeforeSuite(alwaysRun=true)
	public void setRuntime() {
		runtime = String.valueOf(System.currentTimeMillis());
	}
	public static String getRunTime() {
		return runtime;
	}

	@Parameters({"isGrid","deviceQ"})
	@BeforeMethod()
	public void clientSetUp(String isGrid, String deviceQ) throws Exception {
		this.deviceQ = deviceQ;
		initCloudProperties();
		if(testName == null) {
			testName = "DemoTest ";
		}
		Boolean createGrid = Boolean.parseBoolean(isGrid);
		Client tempClient;
		if(createGrid) {
			  GridClient gridClient = new GridClient(cloudProperties.getProperty("accessKey"),cloudProperties.getProperty("url"));
		      tempClient = gridClient.lockDeviceForExecution(testName, deviceQ, 120, TimeUnit.MINUTES.toMillis(2));

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
	
	
	protected String getProperty(String property, Properties props) throws FileNotFoundException, IOException {
		if (System.getProperty(property) != null) {
			return System.getProperty(property);
		} else if (System.getenv().containsKey(property)) {
			return System.getenv(property);
		} else if (props != null) {
			return props.getProperty(property);
		}
		return null;
	}

	private void initCloudProperties() throws FileNotFoundException, IOException {
		cloudProperties = new Properties();
		File cloudPropertiesFile = new File("cloud.properties");
		if(cloudPropertiesFile.exists()) {
			FileReader fr = new FileReader("cloud.properties");
			cloudProperties.load(fr);
			fr.close();
		}else {
			cloudProperties.setProperty("url", System.getenv("url"));
			cloudProperties.setProperty("accessKey", System.getenv("accessKey"));
		}
	
	}


	@AfterMethod()
	public void tearDown() {
			client.generateReport(false);
			client.releaseClient();
		}
	

}
