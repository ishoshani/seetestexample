package com.myTests.myApplication;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.testng.annotations.Test;

public class MyApplicationLogin extends NativeTest2 {
	
	@Test(groups = {"native2"})
	public void test() {
		File f = new File("testLogin2.csv");
		Scanner r = null;
		try {
			r = new Scanner(f);
			r.useDelimiter("\n");
			r.next();
			while(r.hasNext()) {
				String line = r.next();
				String[] tokens = line.split(",");
				String user = tokens[0];
				String password = tokens[1];
				client.elementSendText("default", "topLoginField", 0, user);
				client.elementSendText("default", "bottomLoginField", 0, password);
				client.sendText("{ENTER}");
				client.click("default", "Login", 0, 1);
				boolean clientSuccess = client.isElementFound("default", "Game");
				if(user.equals(password)&&!user.isEmpty()) {
					assertEquals(true, clientSuccess);
					if(client.getDeviceProperty("device.OS").equals("Android")) {
						client.deviceAction("Back");				
					}else {
						client.click("default", "Exit", 0, 1);
					}
				}else {
					assertEquals(false,clientSuccess);
				}
			}}
		catch(IOException e) {
			fail("failed with error"+e);
		}finally {
			r.close();
		}
		System.out.println("got through first part of the test");
	}
}
