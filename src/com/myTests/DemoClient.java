package com.myTests;

import org.junit.Assert;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import com.experitest.auto.BaseTest;
import com.experitest.client.Client;
import com.experitest.client.ClientWrapper;

public class DemoClient extends ClientWrapper {
	String deviceName;
	String appName;
	String runtime;
	String destinationLocation;
	String reporterLocation;
	String installPath;
	public DemoClient(Client client,String app, String sessionTime) {
		super(client);
		runtime = sessionTime; 
		String dv = getDeviceProperty("device.os");
		deviceName = dv.split(":")[0];
		reporterLocation = "FailureReports\\"+"RUN_"+runtime+"\\Devices\\"+deviceName+"\\"+app;
		destinationLocation = System.getProperty("user,dir")+"\\"+reporterLocation;
		setReporter("xml",reporterLocation,app+"test");
	}
	public boolean recordFailure(int status,String installpath,String scenario, String expected, String actual) {
		super.collectSupportData(destinationLocation+status, installpath, deviceName, scenario, expected, actual);

		return true;
	}

	
	
	
}
