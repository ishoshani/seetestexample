package com.myTests.eribank;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.myTests.RetryAnalyzer;

public class NativeTestLogin extends NativeTest {

@Test(groups = {"native1"}, retryAnalyzer = RetryAnalyzer.class)
  public void LoginTest() {
	File f = new File("testLogin.csv");
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
			client.elementSendText("default", "usernameTextField", 0, user);
			client.elementSendText("default", "passwordTextField", 0, password);
			client.click("default", "Login", 0, 1);
			if(client.isElementFound("default", "Close")){
				client.click("default", "Close", 0, 1);
			}
			boolean clientSuccess = client.isElementFound("default", "Make Payment");
			if(user.equals("company") && password.equals("company")) {
				Assert.assertEquals(true, clientSuccess);
			}else {
				Assert.assertEquals(false, clientSuccess);
			}
		}}
	catch(IOException e) {
		Assert.fail("failed with error"+e);
	}finally {
		r.close();
	}

	
  }
}
