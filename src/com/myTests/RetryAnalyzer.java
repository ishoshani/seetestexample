package com.myTests;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
public class RetryAnalyzer implements IRetryAnalyzer{
	private int count =0;
	final private int retryLimit = 2;
	@Override
	public boolean retry(ITestResult arg0) {
		count++;
		if(count<2) {
			return true;
		}else if(count == 2) {
			//reboot
			return true;
		}
		else {
			return false;
		}
	}
	
	

}
