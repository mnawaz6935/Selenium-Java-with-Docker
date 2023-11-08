package com.automation.base;

import org.apache.commons.io.FileUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.asserts.SoftAssert;

import com.automation.listeners.ExtentManager;
import com.automation.pages.AmazonPage;
import com.automation.utilities.Utility;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass extends Utility {

	public static boolean closeDriver = false;
	public static RemoteWebDriver driver;
	public static WebDriverWait wait;
	public static String browser;
	public static String env = "qa";//to do change
	public static FileInputStream fis;
	public static ExtentTest extentReport;
	public static SoftAssert softAssert;
	// This is to set default wait for every method
	public static Integer waitInSeconds = 5;

	// This is the default path to data package
	public static String excelFilePath = System.getProperty("user.dir") + "\\src\\test\\resources\\data\\";

	// This is the default path to imageUpload
	public static String imagePath = System.getProperty("user.dir") + "\\src\\test\\resources\\images\\";
	public static String UtilityscreenshotPath;
	public static String UtilityscreenshotName;
	// This is column name from which we need to get row
	public static final String colName = "env";
	// This is row index of environment column from which we need to get data
	public static int rowIndex = 0;
	public static final int defaultTimeForVisibility = 30;
	public static final int defaultTimeTOBeClickable = 30;
	public static final String AppUrl = "AppURL";
	private static String screenshotPath;
	private static String screenshotNam;

	public static ThreadLocal<RemoteWebDriver> dr = new ThreadLocal<RemoteWebDriver>();

	public static WebDriver getDriver() {
		return dr.get();
	}

	public static void setWebDriver(RemoteWebDriver driver) {
		dr.set(driver);
	}

	public static ExtentTest test;
	private static ExtentReports extent = ExtentManager
			.createInstance(System.getProperty("user.dir") + "/reports/" + "Web Automation.html");

	public static WebDriver initConfiguration() {
		WebDriver localD = null;
		String osName = System.getProperty("os.name");
		if (osName.contains("Windows")) {
			excelFilePath = System.getProperty("user.dir") + "\\src\\test\\resources\\data\\";
			imagePath = System.getProperty("user.dir") + "\\src\\test\\resources\\images\\";
		} else {
			excelFilePath = System.getProperty("user.dir") + "/src/test/resources/data/";
			imagePath = System.getProperty("user.dir") + "/src/test/resources/images/";
		}
		System.out.println("OS : " + osName);
		System.out.println("User Dir : " + System.getProperty("user.dir"));
		System.out.println("excelFilePath  : " + excelFilePath);
		System.out.println("imagePath   : " + imagePath);
		softAssert = new SoftAssert();

		System.out.println("OS : " + System.getProperty("os.name"));
		System.out.println("User Dir : " + System.getProperty("user.dir"));
		if (System.getenv("browser") != null && !System.getenv("browser").isEmpty()) {
			browser = System.getenv("browser");
			System.out.println("Browser: " + browser);
		} else {
			browser = PropertiesReader.getPropertyValue("browser");
		}
		if (System.getenv("env") != null && !System.getenv("env").isEmpty()) {
			env = System.getenv("env");
			System.out.println("Env: " + env);
		} else {
			env = PropertiesReader.getPropertyValue("env");
		}
		if (browser.equals("firefox")) {
			System.setProperty("webdriver.gecko.driver", "gecko.exe");
			localD = new FirefoxDriver();
//			log.debug("Firefox Driver initialized");
		} else if (browser.equals("chrome")) {

			WebDriverManager.chromedriver().browserVersion("108.0.5359.71").setup();
			Map<String, Object> prefs = new HashMap<String, Object>();
			prefs.put("profile.default_content_setting_values.notifications", 2);
			prefs.put("credentials_enable_service", false);
			prefs.put("profile.password_manager_enabled", false);
			ChromeOptions options = new ChromeOptions();
			options.setExperimentalOption("prefs", prefs);
			String agentString = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.45 Safari/537.36";
			options.addArguments("--user-agent=" + agentString);
			options.addArguments("--disable-extensions");
			options.addArguments("--disable-infobars");
			options.addArguments("window-size=1920,1080");
			options.addArguments("--disable-dev-shm-usage"); 
//			options.addArguments("--headless");
			options.addArguments("--ignore-ssl-errors=yes");
			options.addArguments("--ignore-certificate-errors");

			try {
				localD = new ChromeDriver(options);
				//localD = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),options);
			} catch (Exception e) {
				e.printStackTrace();
			}
//			driver.manage().window().maximize();
//			log.debug("Chrome driver intialized");
		} else if (browser.equals("ie")) {
			System.setProperty("webdriver.ie.driver",
					System.getProperty("user.dir") + "\\src\\test\\resources\\executables\\IEDriverServer.exe");
			localD = new InternetExplorerDriver();
		}
		return localD;
	}

	public static String getTimeStamp() {
		return new SimpleDateFormat("dd-M-yyyy hh:mm:ss").format(new Date()).replaceAll("[-: ]", "_");
	}

	public static void AddTest_IntoReport(String TestName, ArrayList<String> test_steps, WebDriver localDriver) {
		test = extent.createTest(TestName);
		for (int i = 0; i < test_steps.size(); i++) {

			if (test_steps.get(i).contains("Failed") || test_steps.get(i).contains("Assertion")) {
				if (test_steps.get(i).toLowerCase().contains("screenshot")) {
					printString("Name" + test_steps.get(i),driver);
					try {

						test.log(Status.FAIL, "<b>" + "<font color=" + "black>" + "Screenshot" + "</font>" + "</b>",
								MediaEntityBuilder.createScreenCaptureFromPath(test_steps.get(i)).build());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else {

					test.log(Status.FAIL, test_steps.get(i));
				}
			} else {
				if (test_steps.get(i).toLowerCase().contains("screenshot")) {
					printString("Name" + test_steps.get(i),driver);
					try {
						test.log(Status.PASS, "<b>" + "<font color=" + "black>" + "Screenshot" + "</font>" + "</b>",
								MediaEntityBuilder.createScreenCaptureFromPath(test_steps.get(i)).build());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else {

					test.log(Status.PASS, test_steps.get(i));
				}
			}
		}
		test_steps.clear();
		closeBrowser(localDriver);

	}

	public static String getScreenshotPath() {
		Date d = new Date();
		return screenshotNam = "ScreenShot" + d.toString().replace(":", "_").replace(" ", "_") + ".jpg";
	}

	public static void captureCapture(WebDriver localDriver, String screen) {

		try {
			File scrFile = ((TakesScreenshot) localDriver).getScreenshotAs(OutputType.FILE);

			Date d = new Date();
			screenshotNam = d.toString().replace(":", "_").replace(" ", "_") + ".jpg";
			try {
				FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir") + "/reports/" + screen));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
		} catch (Exception e) {
			System.err.println(e.getLocalizedMessage());
			// TODO: handle exception
		}
	}
	
	public static void UtilitycaptureScreenshot() throws IOException {

		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		Date d = new Date();
		UtilityscreenshotName = d.toString().replace(":", "_").replace(" ", "_") + ".jpg";

		FileUtils.copyFile(scrFile,
				new File(System.getProperty("user.dir") + "/target/surefire-reports/html/" + UtilityscreenshotName));
	}
	

	@AfterSuite
	public static void afterSuiteFlush() {
		extent.flush();
	}
	
	
	public void login(WebDriver driver,ArrayList<String> testSteps) {
		env = "qa"; //to do change
		String Email = PropertiesReader.getPropertyValue(env + "_" + "Valid_EmailId");
		String Password = PropertiesReader.getPropertyValue(env + "_" + "Valid_Password");

		AmazonPage amazonPage = new AmazonPage(driver);		
	}
	
	public static ArrayList<String> getSortedDecendingStringArray(ArrayList<String> list ) {
		Collections.sort(list,Collections.reverseOrder());
		return list;
	}
	
	public static void clearUsingJavascriptExecutor(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].value = '';", element);
	}

	
	public static ArrayList<Double> getSortedAcendingDoubleArray(ArrayList<Double> list ) {
		Collections.sort(list);
		return list;
	}
	public static ArrayList<String> getSortedAcendingStringArray(ArrayList<String> list ) {
		Collections.sort(list);
		return list;
	}
	public static ArrayList<Double> getSortedDecendingDoubleArray(ArrayList<Double> list ) {
		Collections.sort(list,Collections.reverseOrder());
		return list;
	}
	
	public String getElementCssValue(WebElement element, String attribute,WebDriver driver) {
		waitTime(waitInSeconds);
		waitForElementVisibility(element, Integer.toString(waitInSeconds),driver);
		scrollToElement(element,driver);
		return element.getCssValue(attribute);
	}
	
	public boolean matchStringCaseInsensitiveTrim(String word,String word1) {
		word=word.trim();
		word1=word1.trim();
		return new String(word).equals(word1);
	}

}
