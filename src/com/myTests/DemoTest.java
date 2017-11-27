package com.myTests;

import org.testng.TestRunner;
import org.testng.annotations.BeforeSuite;

public class DemoTest {
	private static String runtime;
	
	@BeforeSuite(alwaysRun=true)
	public void setRuntime() {
		runtime = String.valueOf(System.currentTimeMillis());
	}
	public static String getRunTime() {
		return runtime;
	}
	
	

}
