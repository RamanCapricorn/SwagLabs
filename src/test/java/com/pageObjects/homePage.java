package com.pageObjects;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;


public class homePage {

	public AppiumDriver<MobileElement> mdriver;

	@FindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-Cart drop zone\"]/android.view.ViewGroup/android.widget.TextView")
	@CacheLookup
	public static MobileElement lblProducts;

	public void isProductsVisible() {
		(new WebDriverWait(mdriver, 10))
		.until(ExpectedConditions.visibilityOf(lblProducts));
	}

	//	Code to Swipe UP

	//	public void selectItem(int index) {
	//		ldriver.findElement(By.cssSelector(".grid > div:nth-child("+index+1+")")).click();
	//	}
	//	
	//	public int getSelectedItems() {
	//		int count = ldriver.findElements(By.cssSelector(".grid > div.grid__item--selected")).size();
	//		return count;
	//	}
	//	
	//	public WebElement verifySelectedItem(int index) {
	//		WebElement selected = ldriver.findElement(By.cssSelector(".grid > div.grid__item--selected:nth-child("+index+")"));
	//		return selected;
	//	}
	//	
	//	public void thirdClick() {
	//		(new WebDriverWait(ldriver, 10))
	//				   .until(ExpectedConditions.elementToBeClickable(thirdItem)).click();;
	//	}
	//	
	//	public void isItemNewClickable() {
	//		(new WebDriverWait(ldriver, 10))
	//				   .until(ExpectedConditions.elementToBeClickable(NewItem));
	//	}
}
