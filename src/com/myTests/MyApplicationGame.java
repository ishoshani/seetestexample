package com.myTests;

import static org.testng.Assert.assertEquals;

import java.util.Random;

import org.testng.annotations.Test;

import com.experitest.client.InternalException;

public class MyApplicationGame extends NativeTest2 {
	
	
	@Test(groups = {"native2"})
	public void test2() {
	client.elementSendText("default", "topLoginField", 0, "login");
	client.elementSendText("default", "bottomLoginField", 0, "login");
	client.sendText("{ENTER}");
	client.click("default", "Login", 0, 1);
	client.click("default", "Game", 0, 1);
	for(int j = 0; j < 3; j++) {

		int expectedScore = new Random().nextInt(5);
		int turns  = expectedScore;
		client.waitForElement("default", "clicker_button", 0, 3000);
		for(int i =0; i<turns;i++) {
			playgame:
				try {
					client.click("default", "clicker_button", 0, 1);
				}catch (InternalException e) {
					System.out.println("too slow, lost at"+i);
					expectedScore = i;
					break playgame;
				}
		}
		assertEquals(expectedScore,Integer.parseInt(client.elementGetProperty("default", "counter", 0, "text")));
		client.click("default", "Again", 0, 1);

	}
}
}
