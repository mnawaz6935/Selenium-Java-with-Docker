package com.automation.listeners;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.automation.base.BaseClass;
import com.automation.utilities.SendEmail;
import com.automation.utilities.ZipUtils;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

public class ExtentListeners extends BaseClass implements ITestListener,ISuiteListener {


	
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
	}

	public static void attachScreenShot(String name) {
		// TODO Auto-generated method stub
	}
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
	}

	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
	}

	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
	}

	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
	}

	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
	}
	
	public static void captureErrorAndScreenshot(Throwable result) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onStart(ISuite suite) {
		
		try {
			FileUtils.cleanDirectory(new File(System.getProperty("user.dir")+"/reports/"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
		
	}

	@Override
	public void onFinish(ISuite suite) {

 		Map<String, ISuiteResult> getResults = suite.getResults();
 		ISuiteResult iSuiteResult = getResults.get(getResults.keySet().toArray()[0]);
 		ITestContext iTestContext = iSuiteResult.getTestContext();
 		String nameString = iTestContext.getName();
 		int pass = iTestContext.getPassedTests().size();
 		int fail = iTestContext.getFailedTests().size();
 		int skip = iTestContext.getSkippedTests().size();
 		int total = iTestContext.getAllTestMethods().length;
 		String emailBody = "=============================================================\n"+
 							nameString+"\n"+
 							"Tests Run: "+total+", Passed: "+pass+", Failures: "+fail+", Skipped: "+skip+"\n"+
 							"=============================================================\n";
 		
// 		try {ZipUtils.generateZipFile();
// 			Thread.sleep(3000);
// 		} catch (Exception e) {
// 			e.printStackTrace();
// 		}
	

	}

	public static void takeScreenShot(String name) {
		// TODO Auto-generated method stub
	}

	
}
