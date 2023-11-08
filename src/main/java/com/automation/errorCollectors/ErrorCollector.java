package com.automation.errorCollectors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;

import com.automation.base.BaseClass;
import com.automation.listeners.ExtentListeners;
import com.automation.utilities.Waits;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public class ErrorCollector extends Waits {
	
	private static Map<ITestResult, List<Throwable>> verificationFailuresMap = new HashMap<ITestResult, List<Throwable>>();
	
	public static void assertTrue(boolean condition) {		
			Assert.assertTrue(condition);    	
    }
    
    public static void assertTrue(boolean condition, String message) {
			Assert.assertTrue(condition);    	
    }
    
    public static void assertFalse(boolean condition) {
    		Assert.assertFalse(condition);    	
    }
    
    public static void assertFalse(boolean condition, String message) {
			Assert.assertTrue(condition);    	
    }
    
    public static void assertEquals(boolean actual, boolean expected) {
    		Assert.assertEquals(actual, expected);
    }
    
    public static void assertEquals(Object actual, Object expected) {
    		Assert.assertEquals(actual, expected);
    }
    
    public static void assertEquals(Object[] actual, Object[] expected) {
    		Assert.assertEquals(actual, expected);
    }
    
    public static void assertEquals(Object actual, Object expected, String message) {
    		Assert.assertEquals(actual, expected);
    }
    
    //NotEqual
    
    public static void assertNotEquals(boolean actual, boolean expected) {
    		Assert.assertNotEquals(actual, expected);
    }
    
    public static void assertNotEquals(Object actual, Object expected) {
    		Assert.assertNotEquals(actual, expected);
    }
    
    public static void assertNotEquals(Object[] actual, Object[] expected) {
    		Assert.assertNotEquals(actual, expected);
    }
    
    public static void assertNotEquals(Object actual, Object expected, String message) {
    		Assert.assertNotEquals(actual, expected);
    }
    
//Soft Asserts
    
    public static void softAssertTrue(boolean condition) {
    	BaseClass.softAssert.assertTrue(condition);
    
    }
    
    public static void softAssertTrue(boolean condition, String message) {
    	BaseClass.softAssert.assertTrue(condition, message);
    }
    
    public static void softAssertFalse(boolean condition) {
    	BaseClass.softAssert.assertFalse(condition);
    }
    
    public static void softAssertFalse(boolean condition, String message) {
    	BaseClass.softAssert.assertFalse(condition, message);
    }
    
    public static void softAssertEquals(boolean actual, boolean expected) {
    	BaseClass.softAssert.assertEquals(actual, expected);
    }
    
    public static void softAssertEquals(Object actual, Object expected) {
    	BaseClass.softAssert.assertEquals(actual, expected);
    }
    
    public static void softAssertEquals(Object[] actual, Object[] expected) {
    	BaseClass.softAssert.assertEquals(actual, expected);
    }
    
    public static void softAssertEquals(Object actual, Object expected, String message) {
    	BaseClass.softAssert.assertEquals(actual, expected, message);
    }
    
    public static void fail(String message) {
    	Assert.fail(message);
    }
    
	public static List<Throwable> getVerificationFailures() {
		List<Throwable> verificationFailures = verificationFailuresMap.get(Reporter.getCurrentTestResult());
		return verificationFailures == null ? new ArrayList<Throwable>() : verificationFailures;
	}
	
	public static void addVerificationFailure(Throwable e) {
		List<Throwable> verificationFailures = getVerificationFailures();
		verificationFailuresMap.put(Reporter.getCurrentTestResult(), verificationFailures);
		verificationFailures.add(e);
	}
	
}
