package com.myTests;

import org.testng.annotations.Test;

public class AppStoreCharts extends AppStoreTest{
	@Test(groups = {"Appstore"})
	public void test2() {
		String OS = client.getDeviceProperty("os.device");
		client.deviceAction("HOME");
		client.click("default", "Store", 0, 1);
		client.click("default", "TOP CHARTS", 0, 1);
		if(OS.equals("ANDROID")) {
		client.click("default", "TOP CHARTS", 0, 1);
		client.click("default","toggle_switch_button", 0, 1);
		String[] Titles = new String[10];
		for(int i = 0; i<10; i++) {
				Titles[i] = client.elementGetProperty("NATIVE","xpath=//*[@id='play_card' and @width>0 and ./*[@text='"+(1+i)+"']]//*[@id='li_title']",0,"text");
				if(i%3 == 0) {
					client.swipe("down", 1250, 20);
				}
				System.out.println(Titles[i]);
			}
		}else {
			client.click("default", "Free", 0, 1);
			String[] Titles = new String[10];
			for(int i = 0; i<10; i++) {
				String currentText = client.elementGetProperty("NATIVE", "xpath=//*[starts-with(@text,'"+(i+1)+",')]", 0, "text");
				String[] textTokens = currentText.split(", ");
				Titles[i]=textTokens[1];
				if(i%3 == 0) {
					client.swipe("down", 1250, 20);
				}
				String pathToProof = client.capture();
				client.report(pathToProof,String.format("$d : %s", i,Titles[i]), true);
			}

		}
		
		
	}
	
}
