package com.automation.listeners;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.automation.base.BaseClass;
import com.automation.utilities.MonitoringMail;
import com.automation.utilities.TestConfig;


public class CustomListeners extends BaseClass implements ITestListener,ISuiteListener {

	public 	String messageBody;
	public void onFinish(ITestContext arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onStart(ITestContext arg0) {
		// TODO Auto-generated method stub
		printString("Start test",driver);
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onTestFailure(ITestResult arg0) {

		System.setProperty("org.uncommons.reportng.escape-output","false");
		try {
			UtilitycaptureScreenshot();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Reporter.log("Click to see Screenshot");
		Reporter.log("<a target=\"_blank\" href="+UtilityscreenshotName+">Screenshot</a>");
		Reporter.log("<br>");
		Reporter.log("<br>");
		Reporter.log("<a target=\"_blank\" href="+UtilityscreenshotName+"><img src="+UtilityscreenshotName+" height=200 width=200></img></a>");
	}

	public void onTestSkipped(ITestResult arg0) {
	}


	public void onTestStart(ITestResult arg0) {

//		test = rep.startTest(arg0.getName().toUpperCase());
	
	}

	public void onTestSuccess(ITestResult arg0) {

	}

	public void onFinish(ISuite arg0) {
		
	}

	public void onStart(ISuite arg0) {
		printString("Start suite",driver);
	}

}
