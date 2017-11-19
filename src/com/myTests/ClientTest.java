package com.myTests;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.MethodRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import com.experitest.auto.BaseTest;
import com.experitest.client.Client;

public class ClientTest extends RunnableTest {
	
	
	private String host = "localhost";
    private int port = 8889;
    private String projectBaseDirectory = "C:\\Users\\ido.shoshani\\workspace\\pExperitestDemo";
    private String installPath = "C:\\Program Files (x86)\\Experitest\\SeeTest\\bin\\ipas\\Browser";
    protected DemoClient client = null;
    private String device = null;
    private boolean failed = false;
    private String runtime;
    public ClientTest(String runtime) {
    	super();
    	this.runtime = runtime;
    }
    
    @Override
    void theTests() {
    	test();
    }
   	@Before
	public void setUp() throws Exception {
		client = new DemoClient(host,port,true, runtime);
        client.setProjectBaseDirectory(projectBaseDirectory);
		device = client.waitForDevice("", 30000);
		client.setData(device, "exampleTest",this.getClass());
	}
   

	@Test
	public void test() {
		client.install("com.experitest.ExperiBank/.LoginActivity", true, false);
		client.launch("com.experitest.ExperiBank/.LoginActivity", true, false);
			client.elementSendText("default", "usernameTextField", 0, "company");
			client.elementSendText("default", "passwordTextField", 0, "company");
			client.click("default", "Login", 0, 1);
			System.out.println("status prefail "+getStatus());
			Assert.fail("planned failure");


	}
	@After
	public void tearDown() {
		switch (getStatus()) {
		case 0:
			client.generateReport(true);
			break;
		case -1:
			System.err.println("failed the first time");
			client.recordFailure(getStatus(),installPath,"blah", "bler", "blum");
			client.generateReport(false);
			TestSuite();
			break;
		case -2:
			System.err.println("failed the second time rebooting");
			client.recordFailure(getStatus(),installPath,"blah", "bler", "blum");
			client.generateReport(false);
			client.reboot(120000);
			client.deviceAction("Unlock");
			TestSuite();
			break;
		default:
			client.recordFailure(getStatus(),installPath,"blah", "bler", "blum");
			client.generateReport(true);
			break;
		}
		
		
	
		
	}
}
