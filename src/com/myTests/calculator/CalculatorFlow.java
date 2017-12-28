package com.myTests.calculator;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

public class CalculatorFlow extends CalculatorTest {
	@Test(groups = {"Calculator"})
	public void test3() {
		client.click("default", "Seller Profit Calculator", 0, 1);
		client.swipeWhileNotFound("Up", 761, 602, "NATIVE", "xpath=//*[@text='Listing Information']", 0, 0, 2, false);
		client.click("default", "Auction", 0, 1);
		String[] testNumber = {"100","5","200","2","110"};
		for(int i =4; i>=0;i--) {
			client.elementSendText("NATIVE", xpaths[i], 0, testNumber[i]);
		}
		client.swipeWhileNotFound("Down", 761, 602, "NATIVE", "xpath=//*[@text='Calculate']", 0, 0, 2, false);
		client.click("NATIVE", "xpath=//*[@id='insertion_fee_spinner']", 0, 1);
		client.click("NATIVE", "xpath=//*[@text='$0.15']", 0, 1);
		client.click("NATIVE", "xpath=//*[@id='upgrade_header']", 0, 1);
		client.click("NATIVE", "xpath=//*[@text='International']", 0, 1);
		client.click("NATIVE", "xpath=//*[@text='Reserve price']",0,1);
		client.elementSendText("NATIVE", "xpath=//*[@id='reserve_price_edit_text']", 0, "10");
		client.click("NATIVE", "xpath=//*[@text='Calculate']", 0, 1);
		String profit = client.elementGetText("NATIVE","xpath=//*[@id='final_seller_profit_calc']",0);
		assertEquals("$67.24", profit);



	}
}
