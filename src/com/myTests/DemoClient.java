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
	public DemoClient(String host, int port, boolean useSeesionID,String app, String sessionTime) {
		super(host,port,useSeesionID);
		runtime = sessionTime; 
		String dv = getDeviceProperty("device.os");
		deviceName = dv.split(":")[1];
		reporterLocation = "FailureReports\\"+"RUN_"+runtime+"\\Devices\\"+deviceName+"\\"+app;
		destinationLocation = System.getProperty("user,dir")+"\\"+reporterLocation;
		setReporter("xml",reporterLocation,app+"test");
	}
	public boolean recordFailure(int status,String installpath,String scenario, String expected, String actual) {
		super.collectSupportData(destinationLocation+status, installpath, deviceName, scenario, expected, actual);

		return true;
	}

	
	
	
}
