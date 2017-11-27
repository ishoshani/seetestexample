package com.myTests;

import org.testng.Assert;
import org.testng.annotations.Test;

public class NativeTestWorkFlow extends NativeTest{
	@Test(groups = {"native1"}, retryAnalyzer = RetryAnalyzer.class)
	
	public void OrderTest() {
	client.elementSendText("default", "usernameTextField", 0, "company");
	client.elementSendText("default", "passwordTextField", 0, "company");
	client.click("default", "Login", 0, 1);
	double start = 100;
	double spent = 66;

	String startString = client.hybridRunJavascript("", 0, "var result = document.getElementsByTagName(\"H1\")[0].innerText;");
	startString = startString.replaceAll("[^0-9]", "");
	System.out.println(startString);
	start = Double.parseDouble(startString)/100;
	System.out.print(start);
	client.click("default", "Make Payment", 0, 1);

	client.elementSendText("default", "Phone", 0, "4448416666");
	client.elementSendText("default", "Name", 0, "test");
	client.elementSendText("default", "Amount", 0, "66");
	client.click("default", "Select_1", 0, 1);
	client.click("default", "USA_1", 0, 1);
	client.click("default", "Send Payment_1", 0, 1);
	client.click("default", "Yes_1", 0, 1);
	String actualString = client.hybridRunJavascript("", 0, "var result = document.getElementsByTagName(\"H1\")[0].innerText;");
	actualString = actualString.replace("\n", "");
	String expectedString = String.format("Your balance is: %.2f$",(start-spent));
	Assert.assertEquals(expectedString, actualString);
}
}
