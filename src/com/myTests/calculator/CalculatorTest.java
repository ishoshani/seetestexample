package com.myTests.calculator;

import org.testng.annotations.BeforeMethod;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.*;
import org.testng.asserts.*;

import com.experitest.auto.BaseTest;
import com.experitest.client.Client;
import com.experitest.client.GridClient;
import com.experitest.client.InternalException;
import com.myTests.DemoClient;
import com.myTests.DemoTest;


public class CalculatorTest extends DemoTest{

	
	protected String ErrorString = "+=-_)(*&^%$#@!abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWSYZ;{}[],0987654321../<>";
	protected String[] xpaths = {"xpath=//*[@id='item_cost_edit_text']",
			"xpath=//*[@id='shipping_cost_edit_text']",
			"xpath=//*[@id='sale_price_edit_text']",
			"xpath=//*[@id='buyer_shipping_price_edit_text']",
	"xpath=//*[@id='starting_price_edit_text']"};
	
	public CalculatorTest() {
		super();
		testName = "CalcTest";

		
	}
	@Parameters("isGrid")
	@BeforeMethod
	public void setUp(String isGrid) throws Exception{
		GetApplication();



	}
	public void GetApplication() {
		client.deviceAction("HOME");
		client.click("default", "Store", 0, 1);
		client.sendText("{ENTER}");
		if(client.isElementFound("default", "SearchOut")) {
			 client.click("default","SearchOut",0,1);
			}else {
			client.click("default", "search_button", 0, 1);
			}
        client.elementSendText("default", "Searchstore", 0, "seller profit calculator");
        client.sendText("{ENTER}");
        client.click("NATIVE", "xpath=//*[@id='play_card' and ./*[@text='eBay Seller Profit Calculator']]", 0, 1);
        client.click("NATIVE", "xpath=//*[@text='INSTALL']", 0, 1);
        client.click("NATIVE", "xpath=//*[@text='OPEN']", 0, 1);

	}
	
	
	public void resetApp() {
		if(client.isElementFound("NATIVE", "xpath=//*[@id='reserve_price_edit_text']")) {
			client.click("NATIVE","xpath=//*[@id='upgrade_header']",0,1);
		}
		client.click("NATIVE", "xpath=//*[@id='start_over_button']", 0, 1);
		client.swipe("Up", 761,602);
	}
	@AfterMethod
	public void tearDown() {
			client.generateReport(false);
			client.releaseClient();
		}
	}

