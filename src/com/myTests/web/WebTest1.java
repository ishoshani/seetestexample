package com.myTests.web;
import com.experitest.client.*;
import com.myTests.DemoClient;
import com.myTests.DemoTest;

import org.testng.annotations.*;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

/**
 *
 */
public class WebTest1 extends DemoTest{

	protected String[] Elements = {"Regions","USPolitics","Money","Entertainment","tech","Sport","travel","style","Health","Features","Video","vr", "More…"};
	protected Boolean[] exists = new Boolean[Elements.length];

	public WebTest1() {
		super();
		testName="WebTest";
	}
	@Parameters("isGrid")
	@BeforeMethod(groups = {"Web"})
	public void setUp(String isGrid) throws Exception{

		client.setWebAutoScroll(true);	
		if(OS.equals("IOS_APP")) {
			client.launch("Safari:http://www.cnn.com", true, false);
		}else if(OS.equals("ANDROID")) {
			client.launch("Chrome:http://www.cnn.com", true, true);

		}
		client.waitForElement("WEB", "xpath=//div[@class=\"nav-menu__hamburger\"]", 0, 30000);
		try {
			client.hybridWaitForPageLoad(10000);
		}catch (InternalException e) {
			client.report("chose to not wait for webpage", false);

		}
		if(client.isElementFound("default", "I agree", 0)){
			client.click("default", "I agree", 0, 1);
		}
	        client.click("WEB", "xpath=//div[@class=\"nav-menu__hamburger\"]", 0, 1);

	}

	
	
		
		
		

	 @AfterMethod(groups = {"Web"})
	public void tearDown() {
			client.generateReport(true);
		}	
}
