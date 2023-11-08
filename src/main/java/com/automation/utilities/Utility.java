package com.automation.utilities;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.DataProvider;

import com.automation.base.BaseClass;
import com.automation.base.PropertiesReader;
import com.automation.errorCollectors.ErrorCollector;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.BasicFileReporter;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.Sheet;
import com.google.api.services.sheets.v4.model.Spreadsheet;

public class Utility extends ErrorCollector {
	public static boolean closeDriver = false;
	public static WebDriverWait wait;
	public static String browser;
	public static String env = "qa"; //todo change
	public static final int defaultTimeForVisibility = 30;
	public static final int defaultTimeTOBeClickable = 30;
	// This is column name from which we need to get row
	public static final String colName = "env";
	public static Integer waitInSeconds = 5;
	// This is the default path to data package
	public static String excelFilePath = System.getProperty("user.dir") + "\\src\\test\\resources\\data\\";
	// This is the default path to imageUpload
	public static String imagePath = System.getProperty("user.dir") + "\\src\\test\\resources\\images\\";
	// This is row index of environment column from which we need to get data
	public static int rowIndex = 0;
	

	public static void scrollIntoViewSmoothly(WebElement Element, WebDriver driver) {
		JavascriptExecutor je = (JavascriptExecutor) driver;
		je.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center', inline: 'nearest'})",
				Element);
	}

	public static void openURL(String key, WebDriver driver) {
		driver.get(PropertiesReader.getPropertyValue(env + "_" + key));
				driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Integer.parseInt(PropertiesReader.getPropertyValue("implicit.wait")),
				TimeUnit.SECONDS);
	}

	public static void navigateToURL(String value, WebDriver driver) {
		driver.get(value);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Integer.parseInt(PropertiesReader.getPropertyValue("implicit.wait")),
				TimeUnit.SECONDS);
	}

	public static String loginDetails(String key, WebDriver driver) {
		return PropertiesReader.getPropertyValue(env + "_" + key);
	}

	public static void click(WebElement element, WebDriver driver) {
		scrollIntoViewSmoothly(element, driver);
		waitForElementVisibility(element, "30", driver);
		waitForElementClickable(element, "20", driver);
		element.click();
		Waits.wait5s();
	}

	public static void click(WebElement element, String timeInSeconds, WebDriver driver) {
		waitForElementVisibility(element, timeInSeconds, driver);
		waitForElementClickable(element, timeInSeconds, driver);
		element.click();

	}

	public static void waitForElementVisibility(WebElement element, String timeoutInSeconds, WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, Long.parseLong(timeoutInSeconds));
		wait.until(ExpectedConditions.visibilityOf(element));

	}

	public static void waitForElementClickable(WebElement element, String timeoutInSeconds, WebDriver driver) {
		WebDriverWait waitClickable = new WebDriverWait(driver, Long.parseLong(timeoutInSeconds));
		waitClickable.until(ExpectedConditions.elementToBeClickable(element));

	}

	public void waitUntilSearchLoadingShowing(WebDriver driver) {
		try {
			WebElement element = driver.findElement(By.xpath("//*[@class='css-1bg64dd']"));
			waitUntilElementDisplayed(element, driver);
		} catch (Exception e) {
		}
	}

	public static void waitUntilElementDisplayed(final WebElement webElement, WebDriver driver) {
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		WebDriverWait wait = new WebDriverWait(driver, 20);
		ExpectedCondition elementIsDisplayed = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver arg0) {
				try {
					webElement.isDisplayed();
					return true;
				} catch (NoSuchElementException e) {
					return false;
				} catch (StaleElementReferenceException f) {
					return false;
				}
			}
		};
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}



	public static void type(WebElement element, String value, WebDriver driver) {
		waitUntilElementDisplayed(element, driver);
		element.clear();
		try {
			element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
			element.sendKeys(Keys.DELETE);
			System.out.println("Entered :" + value);
			element.sendKeys(value);
		} catch (Exception e) {
			element.sendKeys(value);
		}

	}

	public static void type_old(WebElement element, String value, WebDriver driver) {
		element.sendKeys(value);
	}

	public static void selectValueFromDropdown(List<WebElement> dropdownValues, String value, WebDriver driver) {
		for (WebElement ele : dropdownValues) {
			System.out.println("Actual :" + ele.getText() + ":");
			System.out.println("Excepted :" + value + ":");
			if (ele.getText().trim().equals(value)) {
				click(ele, driver);
				break;
			}
		}
	}

	public void multipleSelectFromDropDown(WebElement searchBox, List<WebElement> elementList, WebDriver driver,
			String... inputValues) {

		for (String inputValue : inputValues) {
			type(searchBox, inputValue, driver);
			for (WebElement value : elementList) {
				if (value.getText().equals(inputValue)) {
					click(value, driver);
					break;
				}
			}
		}
	}

	public void scrollUp(WebDriver driver) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("scroll(0,-250)");
	}

	public void scrollDown(WebDriver driver) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("scroll(0,2500)");
	}

	public void scrollIntoView(WebElement Element, WebDriver driver) {
		JavascriptExecutor je = (JavascriptExecutor) driver;
		je.executeScript("arguments[0].scrollIntoView();", Element);
	}

	public void clickUsingJavascriptExecutor(WebElement button, WebDriver driver) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", button);
	}

	public void mouseHoverAndClick(WebElement button, WebDriver driver) {
		Actions actions = new Actions(driver);
		actions.moveToElement(button);
		actions.click().build().perform();
	}

	public void mouseHover(WebElement button, WebDriver driver) {
		Actions actions = new Actions(driver);
		actions.moveToElement(button).perform();
	}

	public static boolean isElementDisplayed(WebElement element, WebDriver driver) {
		try {
			if (element.isDisplayed()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	public static boolean isElementSelected(WebElement status, WebDriver driver) {
		if (status.isSelected()) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isElementEnabled(WebElement status, WebDriver driver) {
		if (status.isEnabled()) {
			return true;
		} else {
			return false;
		}
	}

	public Object[][] getData(String filename, String SheetName, WebDriver driver) {
		ExcelReader excel;
		if (System.getProperty("os.name").contains("Windows")) {
			excel = new ExcelReader(
					System.getProperty("user.dir") + "\\src\\test\\resources\\data\\" + filename + ".xlsx");
		} else {
			excel = new ExcelReader(System.getProperty("user.dir") + "/src/test/resources/data/" + filename + ".xlsx");
		}
		int rows = excel.getRowCount(SheetName);
		int columns = excel.getColumnCount(SheetName);

		Object[][] data = new Object[rows - 1][columns];

		for (int rowNum = 2; rowNum <= rows; rowNum++) {
			for (int colNum = 0; colNum < columns; colNum++) {

				data[rowNum - 2][colNum] = excel.getCellData(SheetName, colNum, rowNum);
			}
		}
		return data;
	}

	public Object[][] getSuiteTests(String filename, String SheetName, WebDriver driver) {

		String fullPathString = "";
		if (System.getProperty("os.name").contains("Windows")) {
			fullPathString = System.getProperty("user.dir") + "\\src\\test\\resources\\data\\" + filename + ".xlsm";
		} else {
			fullPathString = System.getProperty("user.dir") + "/src/test/resources/data/" + filename + ".xlsm";
		}

		ExcelReader excel = new ExcelReader(fullPathString);
		int rows = excel.getRowCount(SheetName);
		int columns = excel.getColumnCount(SheetName);

		Object[][] data = new Object[rows - 1][columns];

		for (int rowNum = 2; rowNum <= rows; rowNum++) {
			for (int colNum = 0; colNum < columns; colNum++) {

				data[rowNum - 2][colNum] = excel.getCellData(SheetName, colNum, rowNum);
			}
		}
		return data;
	}

	public void updateTestData(String filename, String sheetName, String testName, String status, WebDriver driver) {

		String fullPathString = "";
		if (System.getProperty("os.name").contains("Windows")) {
			fullPathString = System.getProperty("user.dir") + "\\src\\test\\resources\\data\\" + filename + ".xlsm";
		} else {
			fullPathString = System.getProperty("user.dir") + "/src/test/resources/data/" + filename + ".xlsm";
		}

		ExcelReader excel = new ExcelReader(fullPathString);
		Object[][] data = getSuiteTests(filename, sheetName, driver);
		int rowNum = -1;
		for (int x = 0; x < data.length; x++) {
			if (data[x][1].toString().equals(testName)) {
				rowNum = x + 2;
			}
		}
		printString("Updated Test Result: " + excel.setCellData(sheetName, "Status", rowNum, status), driver);
	}

	public static void quitBrowser(WebDriver driver) {
//		log.info("Quitting browser");
//		ErrorCollector.extentLogInfo("Quitting browser");
		// driver.quit();
	}

	public static void closeBrowser(WebDriver driver) {
		driver.close();
		driver.quit(); 
		closeDriver = true;
	}

	public static String getTitle(WebDriver driver) {
		return driver.getTitle();
	}

	public static void waitforPageLoad(int pageLoadTimeout, WebDriver driver) {
		try {
			new WebDriverWait(driver, pageLoadTimeout).until(webDriver -> ((JavascriptExecutor) webDriver)
					.executeScript("return document.readyState").equals("complete"));
		} catch (TimeoutException e) {
			printString(e.toString(), driver);
		}
	}

	public static String getUniqueData(String value, WebDriver driver) {
		SimpleDateFormat formatter = new SimpleDateFormat("MMddyyHmm");
		Date date = new Date();
		String unique = formatter.format(date).toString();
		String uniqueValue = value + unique;
		formatter.formatToCharacterIterator(uniqueValue);
		return uniqueValue;
	}

	public static String getUniqueEmailId(String value, WebDriver driver) {

		SimpleDateFormat formatter = new SimpleDateFormat("MMddyyHmm");
		Date date = new Date();
		String unique = formatter.format(date).toString();
		String uniqueEmailId = value + unique + "@codeautomation.ai";
		return uniqueEmailId;
	}

	public static String getElementText(WebElement element, WebDriver driver) {
		waitForElementVisibility(element, "10", driver);
		return element.getText().trim();
	}

	public static String getSystemDateInMMDDYYYYFormat(WebDriver driver) {

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDateTime now = LocalDateTime.now();
		System.out.println("Dater : " + dtf.format(now));
		return dtf.format(now);

	}

	public static void teardown(WebDriver driver) {
		closeBrowser(driver);
	}

	public static void getRefreshWebPage(WebDriver driver) {
		waitTime(5000, driver);
		driver.navigate().refresh();
		waitTime(5000, driver);
	}

	// Scroll Into Specific view
	public void scrollIntoSpecificView(WebElement Element, WebDriver driver) {
		JavascriptExecutor je = (JavascriptExecutor) driver;
		je.executeScript("scroll(0,1000)");
	}

	// Scroll Into Specific view
	public void scrollIntoSpecificViewInvoicesAction(WebElement Element, WebDriver driver) {
		JavascriptExecutor je = (JavascriptExecutor) driver;
		je.executeScript("scroll(0,1600)");
	}

	public static void scrollToElement(WebElement element, WebDriver driver) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		((JavascriptExecutor) driver).executeScript("window.scrollTo( 0, -150)");
		boolean isDisplay = isElementDisplayed(element, driver);
		if (!isDisplay) {
			((JavascriptExecutor) driver).executeScript("window.scrollTo( 0, -100)");
		}
	}

	public void click(WebElement element, int timeInSecond, String message, WebDriver driver) {
		waitTime(5000, driver);
		waitForElementVisibility(element, Integer.toString(timeInSecond), driver);
		waitForElementClickable(element, Integer.toString(timeInSecond), driver);
		scrollToElement(element, driver);
		try {
			element.click();
		} catch (Exception e) {
			printString("Failed to Click through click method : " + e.toString(), driver);
			printString("Trying to Click through javascript", driver);
			scrollIntoView(element, driver);
			clickUsingJavascriptExecutor(element, driver);
		}
		printString("Clicked " + message, driver);
	}

	public static void printString(String message, WebDriver driver) {
		try {
			System.out.println(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getElementAttributeValue(WebElement element, String attribute, WebDriver driver) {
		waitTime(40, driver);
		waitForElementVisibility(element, Integer.toString(30), driver);
		scrollToElement(element, driver);
		return element.getAttribute(attribute);
	}

	public void clear(WebElement element, int timeInSecond, String message, WebDriver driver) {

		waitForElementVisibility(element, Integer.toString(timeInSecond), driver);
		scrollToElement(element, driver);
		element.clear();
		printString("Clearing  " + message, driver);
	}

	public static void scrollToAddPaymentsElement(WebElement element, WebDriver driver) {
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
			((JavascriptExecutor) driver).executeScript("scrollBy(0, -350)");
		} catch (Exception e) {
			e.printStackTrace();
			boolean isDisplay = isElementDisplayed(element, driver);
			if (!isDisplay) {
				// ((JavascriptExecutor) driver).executeScript("window.scrollTo( 0, 50)");
			}
		}
	}

	public int getCellRowNum(String fileName, String sheetName, String cellValue) {
		ExcelReader excelReader = new ExcelReader(excelFilePath + fileName + ".xlsx");

		for (int i = 2; i <= excelReader.getRowCount(sheetName); i++) {
			if (excelReader.getCellData(sheetName, colName, i).equalsIgnoreCase(cellValue)) {
				return i;
			}
		}
		return -1;
	}

	public Object[][] loadExcel(String fileName, String sheetName, String env, WebDriver driver) {
		int dataIndex = getCellRowNum(fileName, sheetName, env);
		printString("dataIndex : " + dataIndex, driver);
		assertTrue(dataIndex > -1,
				"Verified Excel sheet '" + sheetName + "' has data for '" + env + "' environment");
		rowIndex = dataIndex - 2;
		return getData(fileName, sheetName, driver);
	}

	public static String generateRandomNumberWithGivenNumberOfDigits(int number, WebDriver driver) {
		String randomNumber = "123456789";
		StringBuilder sb = new StringBuilder(number);
		for (int i = 0; i < number; i++) {
			int index = (int) (randomNumber.length() * Math.random());
			sb.append(randomNumber.charAt(index));
		}
		return sb.toString();
	}

	public static String generateRandomStringWithGivenNumberOfDigits(int number, WebDriver driver) {
		String randomNumber = "qwertyuiopasdfghjklzxcvbnm";
		StringBuilder sb = new StringBuilder(number);
		for (int i = 0; i < number; i++) {
			int index = (int) (randomNumber.length() * Math.random());
			sb.append(randomNumber.charAt(index));
		}
		return sb.toString();
	}

	public void click(WebElement element, int timeInSecond, WebDriver driver) {
		waitTime(5000, driver);
		waitForElementVisibility(element, Integer.toString(timeInSecond), driver);
		waitForElementClickable(element, Integer.toString(timeInSecond), driver);
		scrollToElement(element, driver);
		try {
			element.click();
		} catch (Exception e) {
			printString("Failed to Click through click method : " + e.toString(), driver);
			printString("Trying to Click through javascript", driver);
			clickUsingJavascriptExecutor(element, driver);
		}
	}

	public void type(WebElement element, int timeInSecond, String value, WebDriver driver) {
		waitForElementVisibility(element, Integer.toString(timeInSecond), driver);
		waitForElementClickable(element, Integer.toString(timeInSecond), driver);
		scrollToElement(element, driver);
		element.clear();
		element.sendKeys(value);
	}

	public String getDate(int Day, String formateStr, WebDriver driver) {
		SimpleDateFormat format = new SimpleDateFormat(formateStr);
		final Date date = new Date();
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, Day);
		return format.format(calendar.getTime());
	}

	public void clear(WebElement element, int timeInSecond, WebDriver driver) {

		waitForElementVisibility(element, Integer.toString(timeInSecond), driver);
		scrollToElement(element, driver);
		element.clear();
	}

	public String getInputText(WebElement element, int waitInSeconds, WebDriver driver) {
		waitTime(waitInSeconds, driver);
		waitForElementVisibility(element, Integer.toString(waitInSeconds), driver);
		scrollToElement(element, driver);
		return element.getAttribute("value");
	}

	public void clearThroughRobot(WebElement element, int waitInSeconds, WebDriver driver) {
		click(element, waitInSeconds, driver);
		Robot robot;
		try {
			robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_A);
			robot.keyPress(KeyEvent.VK_DELETE);
		} catch (AWTException e) {
			ErrorCollector.addVerificationFailure(e);
		}

	}

	public void typeKeys(WebElement element, Keys Key, WebDriver driver) {
		waitForElementVisibility(element, Integer.toString(waitInSeconds), driver);
		waitForElementClickable(element, Integer.toString(waitInSeconds), driver);
		scrollToElement(element, driver);
		element.clear();
		element.sendKeys(Key);
	}

	public void goBack(WebDriver driver) {
		driver.navigate().back();
	}

	public static void switchTab(int index, WebDriver driver) {
		ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs2.get(index));

	}

	public static ArrayList<String> getWindows(WebDriver driver) {
		return new ArrayList<String>(driver.getWindowHandles());
	}

	public static void closeTab(int index, WebDriver driver) {
		ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs2.get(index));
		driver.close();
		driver.switchTo().window(tabs2.get(index - 1));

	}

	public Integer sumofList(ArrayList<Integer> list, WebDriver driver) {
		int total = 0;
		for (int i = 0; i < list.size(); i++) {
			total = total + list.get(i);
		}
		return total;
	}

	public static void clearField(WebElement element, WebDriver driver) {
		String inputText = element.getAttribute("value");
		if (inputText != null) {
			for (int i = 0; i < inputText.length(); i++) {
				element.sendKeys(Keys.BACK_SPACE);
			}
		}
	}

	public void sendKeysToWebElement(WebElement element, String keys, WebDriver driver) {
		waitForElementVisibility(element, defaultTimeForVisibility, driver);
		waitForElementToBeClickable(element, defaultTimeTOBeClickable, driver);
		element.clear();
		element.sendKeys(keys);
		Waits.wait1s();
	}

	public static void clearUsingJavascriptExecutor(WebElement element, WebDriver driver) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].value = '';", element);
	}
	
	public static String getDate(int days, String Format, String Time_Zone) throws ParseException {

		DateFormat dateFormat = new SimpleDateFormat(Format);
		dateFormat.setTimeZone(TimeZone.getTimeZone(Time_Zone));
		Date date = new Date();
		String DF = dateFormat.format(date);

		Calendar c = Calendar.getInstance();
		c.setTime(dateFormat.parse(DF));
		c.add(Calendar.DATE, days);
		String formattedDate = dateFormat.format(c.getTime());
		return formattedDate;

	}
	
	public static ArrayList<String> getDateList(int maxdays, String Format, String Time_Zone) throws ParseException {
		ArrayList<String> dates = new ArrayList<>();
		for(int i=0 ; i<= maxdays ; i++) {
			DateFormat dateFormat = new SimpleDateFormat(Format);
			dateFormat.setTimeZone(TimeZone.getTimeZone(Time_Zone));
			Date date = new Date();
			String DF = dateFormat.format(date);
			Calendar c = Calendar.getInstance();
			c.setTime(dateFormat.parse(DF));
			c.add(Calendar.DATE, i);
			String formattedDate = dateFormat.format(c.getTime());
			dates.add(formattedDate);
		}
		return dates;
	}

	/*
	 * 
	 * ' Method Name: <reformatDate> ' Description: <This static method will
	 * take date and its format. then it changes date to required format.> '
	 * Input parameters: <String DateToFormat, String PreFormat, String
	 * PostFormat> ' Return value: <String> ' Created By: <Abu Bakar> '
	 * Created On: <31/01/2022>
	 * 
	 */

	public static String reformatDate(String DateToFormat, String preFormat, String postFormat) throws ParseException {
		DateFormat srcDf = new SimpleDateFormat(preFormat);

		// parse the date string into Date object
		Date date = srcDf.parse(DateToFormat);

		DateFormat destDf = new SimpleDateFormat(postFormat);

		// format the date into another format
		DateToFormat = destDf.format(date);

		return DateToFormat;
	}
	public static String removeDollarandSpaces(String amount , WebDriver driver) {

		printString("Amount :  " + amount,driver);
		if(amount.contains("$")) {
			amount = amount.replace("$", "").trim();
			printString("Remove Dollar :  " + amount,driver);			
		}
		if(amount.contains(",")) {
			amount = amount.replace(",", "").trim();
			printString("Remove Dollar :  " + amount,driver);						
		}
		
		return amount.trim();
	}
	


}
