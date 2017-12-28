package com.myTests.web;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.experitest.client.InternalException;

public class WebTestLinksWork extends WebTest1 {
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
}
