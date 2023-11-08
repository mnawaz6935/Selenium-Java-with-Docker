package com.automation.test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;

import java.util.List;
import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.automation.base.BaseClass;
import com.automation.base.PropertiesReader;
import com.automation.pages.AmazonPage;

public class AmazonTest extends BaseClass {

	String tempSrc = "";

	@Test
	public void AutomateAmazonTest() throws InterruptedException {

		ArrayList<String> testSteps = new ArrayList<>();

		WebDriver driver = initConfiguration();

		AmazonPage amazonPage = new AmazonPage(driver);
		try {

			int step = 1;
			String aboutThisItemTextValues;

			testSteps.add("Step " + (++step) + " : Visit <b>'www.amazon.in'</b> url");
			openURL("AppURL", driver);

			testSteps.add("Step " + (++step) + " : Click On <b>Hamburger</b> Menu");
			amazonPage.clickOnHamburgerMenu(driver);
			
			testSteps.add("Step " + (++step) + " : Click on <b>Tv Appliances Electronics</b> link");
			amazonPage.clickOnTvAppliancesElectronicsLink(driver);
			
			testSteps.add("Step " + (++step) + " : Click On <b>Televisions</b> Menu");
			amazonPage.clickOnTelevisions(driver);
			
			testSteps.add("Step " + (++step) + " : Click On the Brand <b>Samsung</b> Checkbox");
			amazonPage.clickOnBrandSamsungCheckBox(driver);
			
			testSteps.add("Step " + (++step) + " : Click On <b>Sort By</b> dropdown and select <b>High To Low</b> option");
			amazonPage.clickOnSortByAndSelectHighToLowOption(driver);
			
			testSteps.add("Step " + (++step) + " : Click On <b>Second Highest Price Item</b>");
			amazonPage.clickOnSecondHighestPriceItem(driver);			

			testSteps.add("Step " + (++step) + " : Verify <b>'About This Item'</b> label");
			assertTrue(amazonPage.isAboutThisItemDisplaying(driver),
					"Verified <b>'About This Item'</b> label");
			
			aboutThisItemTextValues = amazonPage.getAboutThisItemTextValues(driver);
			testSteps.add("Step " + (++step) + " : Verify <b>'About This Item Text: '</b> "+aboutThisItemTextValues+" label");

			testSteps.add("Step " + (++step) + " : Close the Browser");

			tempSrc = getScreenshotPath();
			testSteps.add(tempSrc);
			captureCapture(driver, tempSrc);
			AddTest_IntoReport("AutomateAmazonTest", testSteps, driver);

		} catch (Exception e) {
			
			testSteps.add("Failed: AutomateAmazonTest "
					+ "<br><b>Exception:</b><br> " + e.toString());
			tempSrc = getScreenshotPath();
			testSteps.add(tempSrc);
			captureCapture(driver, tempSrc);
			AddTest_IntoReport("AutomateAmazonTest", testSteps, driver);
			assertFalse(true);
		} catch (Error e) {
			
			testSteps.add("Failed: AutomateAmazonTest " + "<br><b>Error:</b><br> "
					+ e.toString());
			tempSrc = getScreenshotPath();
			testSteps.add(tempSrc);
			captureCapture(driver, tempSrc);
			AddTest_IntoReport("AutomateAmazonTest", testSteps, driver);
			assertFalse(true);
		}
	}
}
