package com.myTests;

import org.junit.Test;

import com.experitest.auto.BaseTest;
import com.experitest.client.Client;

abstract class RunnableTest extends BaseTest implements Runnable{
	private volatile int status = 0;
	public String testName;
	protected DemoClient client;
	abstract void setUp() throws Exception;
	abstract void theTests() throws AssertionError;
	public void decStatus(){
		status--;
	}
	public void TestSuite() {
			try {
				if(getStatus()==0) {
					setUp();
				}
				theTests();
				status = 0;
			} catch (Exception e) {
				System.out.println("Test Failed");
				status -= 1;

				e.printStackTrace();
			} catch(AssertionError e) {
				System.out.println("Test Failed");
				status -= 1;

				e.printStackTrace();
			}
			finally {
				tearDown();
			}
	}
	public int getStatus() {
		return status;
	}
	public void run() {
		status = 0;
		TestSuite();
	
		}
	


}

