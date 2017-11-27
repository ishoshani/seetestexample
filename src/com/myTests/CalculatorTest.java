package com.myTests;

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


public class CalculatorTest {

	private String host = "localhost";
	private int port = 8889;
	private String projectBaseDirectory = "C:\\Users\\ido.shoshani\\workspace\\pExperitestDemo";
	private String installPath = "";
	private String runtime;
	protected DemoClient client = null;
	private String device = null;
	private String ErrorString = "+=-_)(*&^%$#@!abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWSYZ;{}[],0987654321../<>";
	String[] xpaths = {"xpath=//*[@id='item_cost_edit_text']",
			"xpath=//*[@id='shipping_cost_edit_text']",
			"xpath=//*[@id='sale_price_edit_text']",
			"xpath=//*[@id='buyer_shipping_price_edit_text']",
	"xpath=//*[@id='starting_price_edit_text']"};
	@Parameters("isGrid")
	@BeforeMethod
	public void setUp(String isGrid){
		Boolean createGrid = Boolean.parseBoolean(isGrid);
		Client tempClient;
		if(createGrid) {
			  GridClient gridClient = new GridClient("ido","Espeon123", "", "https://stage.experitest.com:443");
		      tempClient = gridClient.lockDeviceForExecution("Calculator", "@os='Android'", 120, TimeUnit.MINUTES.toMillis(2));

		}else{
	
			tempClient = new Client(host, port, true);
			device = tempClient.waitForDevice("", 30000);
			tempClient.setDevice(device);
		}
		client = new DemoClient(tempClient, "Calculator", runtime);
		client.setProjectBaseDirectory(projectBaseDirectory);
		String apps = client.getInstalledApplications();
		if(!apps.contains("sellerprofitcalculator"));{
			client.openDevice();
			client.launch("chrome:https://play.google.com/store/apps/details?id=com.ralksoft.sellerprofitcalculator&hl=en", true, false);
			client.click("WEB", "text=Open in Play Store app", 0, 1);
			client.click("NATIVE", "xpath=//*[@text='INSTALL']", 0, 1);
			client.waitForElement("default", "OPEN", 0, 60000);
		}
		client.deviceAction("HOME");
		client.setThrowExceptionOnFail(false);
		client.swipeWhileNotFound("right", 400, 400, "default", "Seller Profit Calculator", 0, 0, 10, false);
		client.click("default", "Seller Profit Calculator", 0, 1);



	}
	@Test(groups = {"Calculator"})
	public void test() {
		client.deviceAction("HOME");

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
	public void resetApp() {
		if(client.isElementFound("NATIVE", "xpath=//*[@id='reserve_price_edit_text']")) {
			client.click("NATIVE","xpath=//*[@id='upgrade_header']",0,1);
		}
		client.click("NATIVE", "xpath=//*[@id='start_over_button']", 0, 1);
		client.swipe("Up", 761,602);
	}
	@AfterMethod
	public void tearDown() {
			resetApp();
			client.generateReport(false);
			client.releaseClient();
		}
	}

