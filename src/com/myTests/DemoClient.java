package com.myTests;

import org.junit.Assert;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import com.experitest.auto.BaseTest;
import com.experitest.client.Client;

public class DemoClient extends Client {
	String deviceName;
	String appName;
	String runtime;
	String destinationLocation;
	String reporterLocation;
	String installPath;
	Class testClass;
	public DemoClient(String host, int port, boolean useSeesionID, String sessionTime) {
		super(host,port,useSeesionID);
		runtime = sessionTime; 
	}
	public void setData(String dv, String app, Class testClass, String installPath) {
		this.testClass = testClass;
		deviceName = dv.split(":")[1];
		System.out.println(deviceName);
		appName = app;
		this.installPath = installPath;
		reporterLocation = "FailureReports\\"+"RUN_"+runtime+"\\Devices\\"+deviceName+"\\"+appName;
		destinationLocation = System.getProperty("user,dir")+"\\"+reporterLocation;
		setReporter("xml",reporterLocation,app+"test");


	}
	public boolean recordFailure(int status,String installpath,String scenario, String expected, String actual) {
		super.collectSupportData(destinationLocation+status, installpath, deviceName, scenario, expected, actual);

		return true;
	}
	public boolean isElementFoundTest(int status, String zone,String element, int index) {
		boolean check = super.isElementFound(zone, element, index);
		if(!check) {
			recordFailure(status,installPath, "Looking for Element Existence","Expected to find element "+element,"did not find it");
			Assert.fail("failed to find element"+element);
		}
		return check;
	}
	public boolean AssertStringsEqual(int status, String expected, String actual) {
		try {
			Assert.assertEquals(expected, actual);
		}catch(AssertionError e) {
			recordFailure(status,installPath,"Failed to match Strings",expected,actual);
			throw e;
		}
		return true;
	}
	public boolean AssertBoolean(int status , boolean expected, boolean actual) {
		try {
			Assert.assertEquals(expected, actual);
		}catch(AssertionError e) {
			recordFailure(status,installPath,"Failed to match Booleans",String.valueOf(expected),String.valueOf(actual));
			throw e;
		}
		return true;
	}
	public boolean AssertIntsEqual(int status, int expected, int actual) {
		try {
			Assert.assertEquals(expected, actual);
		}catch(AssertionError e) {
			recordFailure(status,installPath,"Failed to match Booleans",String.valueOf(expected),String.valueOf(actual));
			throw e;
		}
		return true;		
	}
	
	
	
}
