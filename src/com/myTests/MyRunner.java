package com.myTests;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import org.boon.primitive.Int;
import org.junit.runner.Runner;
import org.testng.internal.thread.TestNGThread;

import java.util.ArrayDeque;
import java.util.HashMap;


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
	
	
	}
