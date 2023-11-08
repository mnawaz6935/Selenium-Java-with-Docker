package com.automation.pages;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.automation.base.BaseClass;

public class AmazonPage extends BaseClass {

	@FindBy(xpath = ("//a[@id='nav-hamburger-menu']"))
	WebElement hamburgerMenu;
	
	@FindBy(xpath = ("//div[text()='TV, Appliances, Electronics']"))
	WebElement tvAppliancesElectronicsLink;
	
	@FindBy(xpath = ("//div[text()='tv, audio & cameras']"))
	WebElement tvAudioAndCamerasSubSection;
	
	@FindBy(xpath = ("//a[text()='Televisions']"))
	WebElement televisions;
	
	@FindBy(xpath = ("//span[text()='Samsung']"))
	WebElement brandSamsungCheckBox;
	
	@FindBy(xpath = ("//span[contains(text(),'Sort by:')]"))
	WebElement sortByDropdown;
	
	@FindBy(xpath = ("//a[text()='Price: High to Low']"))
	WebElement highToLowOption;
	
	@FindBy(xpath = ("(//div[@class='a-section a-spacing-base'])[2]"))
	WebElement secondHighestPriceItem;
	
	@FindBy(xpath = ("//h1[contains(text(),' About this item ')]"))
	WebElement aboutThisItem;
	
	@FindBy(xpath = ("//h1[contains(text(),' About this item ')]/following-sibling::ul"))
	WebElement aboutThisItemText;	

	@FindBy(xpath = ("//div[text()='trending']"))
	WebElement trendingLbl;
	
	@FindBy(xpath = ("//span[text()='Category']"))
	WebElement categoryLbl;	

	
	public AmazonPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	public void clickOnHamburgerMenu(WebDriver driver) {
		waitForElementClickable(hamburgerMenu, "20",driver);
		click(hamburgerMenu,driver);
	}
	
	public void clickOnTvAppliancesElectronicsLink(WebDriver driver) {
		waitForElementVisibility(trendingLbl, "30", driver);
		scrollIntoViewSmoothly(tvAppliancesElectronicsLink, driver);		
		waitForElementClickable(tvAppliancesElectronicsLink, "20",driver);
		click(tvAppliancesElectronicsLink,driver);
	}
	
	public void clickOnTelevisions(WebDriver driver) {
		waitForElementVisibility(tvAudioAndCamerasSubSection, "30", driver);
		scrollIntoViewSmoothly(televisions, driver);		
		waitForElementClickable(televisions, "20",driver);
		click(televisions,driver);
	}
	
	public void clickOnBrandSamsungCheckBox(WebDriver driver) {
		waitForElementVisibility(categoryLbl, "30", driver);
		scrollIntoViewSmoothly(brandSamsungCheckBox, driver);		
		waitForElementClickable(brandSamsungCheckBox, "20",driver);
		click(brandSamsungCheckBox,driver);
	}
	
	public void clickOnSortByAndSelectHighToLowOption(WebDriver driver) {
		waitForElementVisibility(sortByDropdown, "30", driver);
		click(sortByDropdown,driver);
		waitForElementVisibility(highToLowOption, "30", driver);
		click(highToLowOption,driver);
	}

	public void clickOnSecondHighestPriceItem(WebDriver driver) {
		waitForElementVisibility(secondHighestPriceItem, "30", driver);
		click(secondHighestPriceItem,driver);
	}
	
	public boolean isAboutThisItemDisplaying(WebDriver driver) {
		ArrayList<String> tabs2 = new ArrayList<String> (driver.getWindowHandles());
		driver.switchTo().window(tabs2.get(tabs2.size()-1));
		waitForElementVisibility(aboutThisItem, "30", driver);
		return isElementDisplayed(aboutThisItem,driver);
	}
	
	public String getAboutThisItemTextValues(WebDriver driver) {
		scrollIntoViewSmoothly(aboutThisItemText, driver);		
		waitForElementVisibility(aboutThisItemText, "30", driver);
		return getElementText(aboutThisItemText, driver);
	}	
}
