package com.myTests;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

public class CalculatorView extends CalculatorTest {
	@Test(groups = {"Calculator"})
	public void test2() {
		client.setThrowExceptionOnFail(true);
		client.swipeWhileNotFound("Up", 761, 602, "NATIVE", "xpath=//*[@text='Listing Information']", 0, 0, 2, false);
		client.click("default", "Fixed price", 0 , 1);
		boolean found = client.isElementFound("default", "AuctionStartingPrice", 0);
		assertEquals(false,found);
		client.click("default", "Auction", 0, 1);
		client.isElementFound("default", "AuctionStartingPrice", 0);
		client.swipeWhileNotFound("Down", 761, 602, "NATIVE", "xpath=//*[@text='CALCULATE']", 0, 0, 2, false);
		client.click("NATIVE", "xpath=//*[@id='arrow_icon']", 0, 1);
		client.click("NATIVE", "xpath=//*[@text='Reserve price']", 0, 1);
		client.isElementFound("default", "reservemarker", 0);
	}
}
