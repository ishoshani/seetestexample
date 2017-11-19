package com.myTests;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import com.experitest.client.Client;

public class FailureRecorder extends TestWatcher {
	private static String ReportPath;
	@Override
	public void failed(Throwable e, Description description) {
		
		
	}

}
