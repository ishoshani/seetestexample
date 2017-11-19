package com.myTests;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import org.boon.primitive.Int;
import org.junit.runner.Runner;
import java.util.ArrayDeque;
import java.util.HashMap;

import com.google.common.io.Files;

public class MyRunner{
		static String runtime;
		public static String SummaryReport(int[] statuses, String[] testnames) {
			String reporterLocation = "FailureReports\\"+"RUN_"+runtime+"\\SummaryReport.txt";
			String destinationLocation = System.getProperty("user.home")+"\\seetest-reports\\"+reporterLocation;
			File SummaryFile = new File(destinationLocation);
			SummaryFile.getParentFile().mkdirs();
			PrintWriter pw = null;
			try {
				SummaryFile.createNewFile();
				pw = new PrintWriter(SummaryFile);
				pw.println("Overall Test Summary");
				for(int i = 0; i<statuses.length; i++) {
					String start = "Test "+testnames[i]+":  ";
					switch (statuses[i]) {
					
					case 0:
						pw.println(start+"passed");
						break;
					case -1:
						pw.println(start+"passed on second attempt");
						break;
					case -2:
						pw.println(start+"passed after reboot");
						break;
					default:
						pw.println(start+"did not pass after reboot");
						break;
					}
					 
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				pw.close();
			}
			System.out.println(destinationLocation);
			return destinationLocation;
		}
	
		public static void main(String... args) {
			runtime = ""+System.currentTimeMillis();
			ArrayDeque<RunnableTest> threadq = new ArrayDeque<RunnableTest>();
			HashMap<Integer,RunnableTest> finished = new HashMap<Integer,RunnableTest>();
			//Create one of each test
			RunnableTest st1 = new AppStoreTest(runtime);
			RunnableTest nt1 = new NativeTest(runtime);
			RunnableTest nt2 = new NativeTest2(runtime);
			RunnableTest ct1 = new CalculatorTest(runtime);
			RunnableTest wt1 = new WebTest1(runtime);
		
			//	add them all to que
			threadq.add(st1);
			threadq.add(nt1);
			threadq.add(nt2);
			threadq.add(ct1);
			threadq.add(wt1);
			int index = 0;
			while(!threadq.isEmpty()) {
				//run two test tests in parallel
			RunnableTest currentTest1= threadq.remove();
			RunnableTest currentTest2 = threadq.remove();
					
			Thread t1 = new Thread(currentTest1);
			Thread t2 = new Thread(currentTest2);
			t1.start();
			t2.start();
			try {
				//wait for both to finish
			t1.join();
			t2.join();
			}catch(InterruptedException e){
				e.printStackTrace();
				System.exit(1);
			}
			finished.put(index, currentTest1);
			finished.put(index+1, currentTest2);
			index+=2;
			}
			int[] s = new int[4];
			String[] n = new String[4];
			for(Integer i : finished.keySet()) {
				RunnableTest current = finished.get(i);
				System.out.println(current.getStatus());
				s[i]=current.getStatus();
				n[i]=current.testName;
			}
			SummaryReport(s,n);
			

			
			

		}
	}

