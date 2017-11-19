package com.experitest.auto;

import org.junit.Before;
import org.junit.Test;


public class AndroidDemoTest extends BaseTest {

	@Before
	public void setUp() throws Exception{
		init("@os='android'", "AndroidDemoTest");
	}
	
	@Test
	public void test(){
		
	}
	
	
}
