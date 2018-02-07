package com.myTests.calculator;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

public class CalculatorField extends CalculatorTest {
	@Test(groups = {"Calculator"})
	public void test() {

		client.startVideoRecord();
		for(int i = 4; i>=0;i--) {
			client.swipeWhileNotFound("Down", 761, 400, "NATIVE", xpaths[i], 0, 0, 2, false);
			client.elementSendText("NATIVE",xpaths[i],0,ErrorString);
			String endString = client.elementGetProperty("NATIVE", xpaths[i], 0, "text");
			assertTrue("0987654321.".equals(endString));
		}
		client.swipeWhileNotFound("Down", 761, 602, "NATIVE", "xpath=//*[@text='CALCULATE']", 0, 0, 2, false);
		client.click("NATIVE", "xpath=//*[@id='arrow_icon']", 0, 1);
		client.click("NATIVE", "xpath=//*[@text='Reserve price']", 0, 1);
		client.elementSendText("NATIVE", "xpath=//*[@id='reserve_price_edit_text']", 0, ErrorString);
		String endString = client.elementGetProperty("NATIVE", "xpath=//*[@id='reserve_price_edit_text']", 0, "text");
		assertTrue("0987654321.".equals(endString));
		client.click("NATIVE", "xpath=//*[@text='Calculate']", 0, 1);
		client.stopVideoRecord();
		resetApp();

	} 
}
