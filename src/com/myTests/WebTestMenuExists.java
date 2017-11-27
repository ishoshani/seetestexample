package com.myTests;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

public class WebTestMenuExists extends WebTest1{
	@Test(groups = {"Web"})
	public void testWebTest1(){
		//Opena pp at page
		for(int i =0; i<Elements.length; i++) {
			exists[i]=client.isElementFound("default", Elements[i], 0);
			assertTrue(exists[i]);
		
		}
	}
}
