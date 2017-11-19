package com.myTests;
import com.experitest.auto.BaseTest;
import com.experitest.client.*;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.http.client.ClientProtocolException;
import org.junit.*;
/**
 *
 */
public class WebTest1 extends RunnableTest {
	private String host = "localhost";
	private int port = 8889;
	private String projectBaseDirectory = "C:\\Users\\ido.shoshani\\workspace\\pExperitestDemo";
	private String installPath = "";
	private String runtime;
	protected DemoClient client = null;
	private String device;
	private String OS;
	public WebTest1(String runtime) {
		this.runtime= runtime;
	}
	@Override
	void theTests() throws AssertionError {
		testWebTest1();
	}
	@Before
	public void setUp(){
		client = new DemoClient(host, port, true, runtime);
		client.setProjectBaseDirectory(projectBaseDirectory);
		device = client.waitForDevice("", 30000);

		client.setDevice(device);
		OS = client.getDeviceProperty("device.os");
		client.setWebAutoScroll(true);
	
		client.setData(device, "Web", this.getClass(), installPath);
	}

	@Test
	public void testWebTest1(){
		//Opena pp at page
			if(OS.equals("IOS_APP")) {
			client.launch("Safari:http://www.cnn.com", true, false);
		}else if(OS.equals("ANDROID")) {
			client.launch("Chrome:http://www.cnn.com", true, true);

		}
		
		try {
			client.hybridClearCache(true, true);
			client.hybridWaitForPageLoad(10000);
		}catch (InternalException e) {
			client.report("chose to not wait for webpage", false);

		}
		if(client.isElementFound("default", "Done", 0)){
			client.click("default", "Done", 0, 1);
		}
		if(client.isElementFound("default", "I agree", 0)){
			client.click("default", "I agree", 0, 1);
		}
		//Test that all side menu buttons exists
		String[] Elements = {"Regions","USPolitics","Money","Entertainment","tech","Sport","travel","style","Health","Features","Video","vr", "More…"};
		Boolean[] exists = new Boolean[Elements.length];
		client.click("WEB", "xpath=//div[@class=\"nav-menu__hamburger\"]", 0, 1);

		for(int i =0; i<Elements.length; i++) {
			exists[i]=client.isElementFoundTest(getStatus(), "default", Elements[i], 0);
		
		}
		String[] urls = {"edition.cnn.com/regions","edition.cnn.com/politics","money.cnn.com/INTERNATIONAL/",
				"edition.cnn.com/entertainment","money.cnn.com/technology/","edition.cnn.com/sport","edition.cnn.com/travel",
				"edition.cnn.com/style","edition.cnn.com/health","edition.cnn.com/specials","edition.cnn.com/videos","edition.cnn.com/vr","edition.cnn.com/more"};
		if(OS.equals("IOS_APP_Old")) {
			//Keeping this code in case i decide i do want to actually navigate to the webpages.
		for(int i = 0; i<Elements.length;i++) {
			client.click("default", Elements[i], 0, 1);
			try {
				client.hybridWaitForPageLoad(10000);
			}catch (InternalException e) {
				client.report("chose to not wait for webpage", false);

			}

			String currentURL = client.elementGetProperty("default", "URLbar", 0, "text");
			Assert.assertTrue("Failed to match URL "+currentURL+" to "+urls[i],currentURL.equals("http://"+urls[i]));
			client.click("default", "backIcon", 0, 1);
			if(client.isElementFound("default", "Done", 0)){
				client.click("default", "Done", 0, 1);
			}
			try{
				client.waitForElement("WEB", "xpath=//*[@text='Home']", 0, 100000);
				client.click("WEB", "xpath=//div[@class=\"nav-menu__hamburger\"]", 0, 1);
			}catch(InternalError e) {
				client.report("did not wait for loading, could not find home hamburger menu", false);
			}
		}
	}else{
		//test that each button has the expected href link
		for(int i =0;i<Elements.length;i++) {
			String href = client.elementGetProperty("WEB", "xpath=//*[@class='nav-flyout__section-title']", i, "href");
			try {
			client.AssertBoolean(getStatus(), true, (urls[i].contains(href) || href.contains(urls[i])));
			}catch(AssertionError e) {
			client.report("URL "+href+" is not in "+urls[i],false);
			throw e;
			}
		}
	}
	}
		
		
		

	 @After
		public void tearDown() {
			switch (getStatus()) {
			case 0:
				//didn't fail, 
				client.generateReport(true);
				break;
			case -1:
				//took a second try
				System.err.println("failed the first time");
				client.generateReport(false);
				TestSuite();
				break;
			case -2:
				//failed on second try, reboot
				System.err.println("failed the second time rebooting");
				client.generateReport(false);
				try {
					client.reboot(120000);
					client.deviceAction("Unlock");
					}catch(InternalException e) {
						decStatus();
					}
				TestSuite();
				break;
			default:
				client.generateReport(true);
				break;
			}
	    }
}
