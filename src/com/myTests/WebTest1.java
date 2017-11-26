package com.myTests;
import com.experitest.client.*;

import org.testng.annotations.*;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

/**
 *
 */
public class WebTest1{
	private String host = "localhost";
	private int port = 8889;
	private String projectBaseDirectory = "C:\\Users\\ido.shoshani\\workspace\\pExperitestDemo";
	private String runtime;
	protected Client client = null;
	private String device;
	private String OS;
	private String[] Elements = {"Regions","USPolitics","Money","Entertainment","tech","Sport","travel","style","Health","Features","Video","vr", "More…"};
	private Boolean[] exists = new Boolean[Elements.length];


	@Parameters("isGrid")
	@BeforeMethod(groups = {"Web"})
	public void setUp(String isGrid){
			Boolean createGrid = Boolean.parseBoolean(isGrid);
			if(createGrid) {
				  GridClient gridClient = new GridClient("ido","Espeon123", "WebTest", "https://stage.experitest.com:443");
			      client = gridClient.lockDeviceForExecution("native1", "", 120, TimeUnit.MINUTES.toMillis(2));

			}else{
		
		client = new DemoClient(host, port, true,"WebTest", runtime);
		device = client.waitForDevice("", 30000);

		client.setDevice(device);
			}
		client.setProjectBaseDirectory(projectBaseDirectory);
	
		OS = client.getDeviceProperty("device.os");
		client.setWebAutoScroll(true);
	
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
		client.click("WEB", "xpath=//div[@class=\"nav-menu__hamburger\"]", 0, 1);
	}

	@Test(groups = {"Web"})
	public void testWebTest1(){
		//Opena pp at page
		for(int i =0; i<Elements.length; i++) {
			exists[i]=client.isElementFound("default", Elements[i], 0);
			assertTrue(exists[i]);
		
		}
	}
	@Test(groups = {"Web"})
	public void testWebTest2() {
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
			assertEquals(currentURL,"http://"+urls[i]);
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
			assertTrue(urls[i].contains(href) || href.contains(urls[i]));
			}catch(AssertionError e) {
			client.report("URL "+href+" is not in "+urls[i],false);
			throw e;
			}
		}
	}
	}
		
		
		

	 @AfterMethod(groups = {"Web"})
	public void tearDown() {
			client.generateReport(true);
		}	
}
