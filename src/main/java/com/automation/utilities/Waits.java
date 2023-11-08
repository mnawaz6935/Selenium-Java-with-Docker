package com.automation.utilities;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Waits {

    public static void wait1s()  {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void wait2s()  {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void wait3s() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void wait4s()  {
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void wait5s()  {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void wait6s() {
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
	public static void waitTime(int time) {
		try {
			Thread.sleep(time);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	public void waitForElementToBeClickable(WebElement element, int timeOutInSeconds, WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	public static void waitForElementVisibility(WebElement element, int timeoutInSeconds, WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
		wait.until(ExpectedConditions.visibilityOf(element));
	}
	public static void waitfor10sec() throws InterruptedException {
		Thread.sleep(10000);
	}

	public static void waitfor10mints() throws InterruptedException {
		Thread.sleep(600000);
	}

	public static void waitfor3sec() throws InterruptedException {
		Thread.sleep(3000);
	}

	public static void waitfor5sec() throws InterruptedException {
		Thread.sleep(5000);
	}

	public static void waitfor1sec() throws InterruptedException {
		Thread.sleep(1000);
	}
	public static void waitTime(int time, WebDriver driver) {
		try {
			Thread.sleep(time);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	public void Waitfor10mints() throws InterruptedException {
		Thread.sleep(3000);
	}
}
