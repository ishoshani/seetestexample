package com.myTests;
import com.experitest.client.*;

import org.testng.annotations.*;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

/**
 *
 */
public class WebTest1 extends DemoTest{
	private String host = "localhost";
	private int port = 8889;
	private String projectBaseDirectory = "C:\\Users\\ido.shoshani\\workspace\\pExperitestDemo";
	private String runtime;
	protected DemoClient client = null;
	private String device;
	protected String OS;
	protected String[] Elements = {"Regions","USPolitics","Money","Entertainment","tech","Sport","travel","style","Health","Features","Video","vr", "More…"};
	protected Boolean[] exists = new Boolean[Elements.length];


	@Parameters("isGrid")
	@BeforeMethod(groups = {"Web"})
	public void setUp(String isGrid){
			runtime = getRunTime();
			Boolean createGrid = Boolean.parseBoolean(isGrid);
			Client tempClient;
			if(createGrid) {
				  GridClient gridClient = new GridClient("ido","Espeon123", "", "https://stage.experitest.com:443");
			      tempClient = gridClient.lockDeviceForExecution("Web1", "", 120, TimeUnit.MINUTES.toMillis(2));

			}else{
		
				tempClient = new Client(host, port, true);
				device = tempClient.waitForDevice("", 30000);
				tempClient.setDevice(device);
			}
		client = new DemoClient(tempClient, "Web", runtime);
		client.setProjectBaseDirectory(projectBaseDirectory);
	
		OS = client.getDeviceProperty("device.os");
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
