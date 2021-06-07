package com.testCases;

import java.awt.AWTException;
import java.io.IOException;
import java.util.Date;
import java.util.Hashtable;

import org.openqa.selenium.By;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.base.BaseClass;
import com.relevantcodes.extentreports.LogStatus;

import io.appium.java_client.MobileElement;

public class PositiveTestcase extends BaseClass {

	int i=0; 

	@BeforeClass
	public void Testbefore() throws IOException, InterruptedException, AWTException {

		if(i==0) {
			TestBeforeMethod();
		}
		i=i+1;  
	}

	@AfterClass
	public void afterMethod(){

		if(i==0 || i==RowsCount()) {
			TestAfterMethod();
		}
	}

	@Test(dataProvider="SwagLabs Positive Scenario",dataProviderClass=DataProviderClass.class, priority = 1)
	public void SwagLabsPositiveScenario(Hashtable<String,String> data) throws AWTException, InterruptedException {
		try {
			ImplicitWait();
			testCaseName="SwagLabs Positive Scenario";
			Date d = new Date();
			String TestCaseName = testCaseName + "-" + data.get("Environment") + "_" + d.toString().replace(":", "_").replace(" ", "_") + ".html";
			String TestCaseDesc = testCaseName + "-" + data.get("Environment");
			String TestCaseComDesc = testCaseName + "-" + data.get("Environment");

			StartReport(TestCaseDesc, TestCaseComDesc);
			isTestRunnable(data);

			System.out.println("App run successfully.");

			MobileElement username = driver.findElementByAccessibilityId("test-Username");
			MobileElement password = driver.findElementByAccessibilityId("test-Password");
			MobileElement Login = driver.findElementByAccessibilityId("test-LOGIN");

			test.log(LogStatus.INFO, "User is on Login screen.");
			CaptureScreen();

			username.sendKeys(data.get("Username"));
			password.sendKeys(data.get("Password"));
			Login.click();

			driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"test-Cart drop zone\"]/android.view.ViewGroup/android.widget.TextView")).isDisplayed();

			test.log(LogStatus.INFO, "User is on Products screen.");
			CaptureScreen();

			driver.findElement(By.xpath("(//android.view.ViewGroup[@content-desc=\"test-ADD TO CART\"])[1]")).click(); 	// First Product selected
			driver.findElement(By.xpath("(//android.view.ViewGroup[@content-desc=\"test-ADD TO CART\"])[1]")).click();	// Second Product selected
			scrollToElement(By.xpath("(//android.view.ViewGroup[@content-desc=\"test-ADD TO CART\"])[1]"));				// Scroll to Third Product
			driver.findElement(By.xpath("(//android.view.ViewGroup[@content-desc=\"test-ADD TO CART\"])[1]")).click();	// Third Product selected

			driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"test-Cart\"]/android.view.ViewGroup/android.widget.ImageView")).click();	// Cart Icon click

			driver.findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.widget.TextView")).isDisplayed();

			test.log(LogStatus.INFO, "User is on Cart Details screen.");
			CaptureScreen();

			if(driver.findElement(By.xpath("(//android.view.ViewGroup[@content-desc=\"test-REMOVE\"])[2]")).isDisplayed())
				driver.findElement(By.xpath("(//android.view.ViewGroup[@content-desc=\"test-REMOVE\"])[2]")).click();

			scrollToElement("test-CHECKOUT");		// Scroll to Checkout Button
			driver.findElementByAccessibilityId("test-CHECKOUT").click();

			driver.findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.widget.TextView")).isDisplayed();
			test.log(LogStatus.INFO, "User is on Checkout Information screen.");
			CaptureScreen();

			if(driver.findElementByAccessibilityId("test-First Name").isDisplayed())
				driver.findElementByAccessibilityId("test-First Name").sendKeys("Raman");
			driver.findElementByAccessibilityId("test-Last Name").sendKeys("Kumar");
			driver.findElementByAccessibilityId("test-Zip/Postal Code").sendKeys("140301");

			driver.findElementByAccessibilityId("test-CONTINUE").click();

			driver.findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.widget.TextView")).isDisplayed();
			test.log(LogStatus.INFO, "User is on Checkout Overview screen.");
			CaptureScreen();

			scrollToElement("test-FINISH");		// Scroll to Finish Button
			driver.findElementByAccessibilityId("test-FINISH").click();

			if(driver.findElement(By.xpath("//android.widget.ScrollView[@content-desc=\"test-CHECKOUT: COMPLETE!\"]/android.view.ViewGroup/android.widget.TextView[1]")).isDisplayed())
			{
				int rowNum=Integer.parseInt(data.get("row"));
				Result_to_Xls(xls,"TestData",rowNum,"Pass","Result");
				test.log(LogStatus.PASS, "User is on Thank you screen.");
				CaptureScreen();
			}
			else {
				int rowNum=Integer.parseInt(data.get("row"));
				Result_to_Xls(xls,"TestData",rowNum,"Fail","Result");
				test.log(LogStatus.FAIL, "User could not reach to the Thank you page.");
				CaptureScreen();
			}

			driver.findElementByAccessibilityId("test-BACK HOME").click();
			test.log(LogStatus.INFO, "User is back to Products for more shopping.");
			CaptureScreen();

			driver.findElementByAccessibilityId("test-Menu").click();
			driver.findElementByAccessibilityId("test-LOGOUT").click();

			if(driver.findElementByAccessibilityId("test-LOGIN").isDisplayed()) {
				test.log(LogStatus.PASS, "User is logout successfully.");
				CaptureScreen();
			}
			else
			{
				test.log(LogStatus.FAIL, "User could not logout.");
				CaptureScreen();
			}
		}
		catch(Exception e){

			System.out.println("Error Message:"+ e.getMessage());

			if(e.getMessage().equals("Skipping the test as runmode is N"))
			{
				reportSkip(e.getMessage());
				int rowNum=Integer.parseInt(data.get("row"));
				Result_to_Xls(xls,"TestData",rowNum,"Skip","Result");	
			}
			else							
			{
				reportFail(e.getMessage());
				CaptureScreen();
				int rowNum=Integer.parseInt(data.get("row"));
				Result_to_Xls(xls,"TestData",rowNum,"Fail","Result");
			}
		}
	}

}
