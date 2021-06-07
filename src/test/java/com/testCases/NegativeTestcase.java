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

public class NegativeTestcase extends BaseClass {
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
	
	@Test(dataProvider="SwagLabs Negative Scenario",dataProviderClass=DataProviderClass.class, priority = 2)
	public void SwagLabsNegativeScenario(Hashtable<String,String> data) throws AWTException, InterruptedException {
		try {
			ImplicitWait();
			testCaseName="SwagLabs Negative Scenario";
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

			driver.findElement(By.xpath("//android.view.ViewGroup[@content-desc=\"test-Cart\"]/android.view.ViewGroup/android.widget.ImageView")).click();

			driver.findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.widget.TextView")).isDisplayed();
			
			test.log(LogStatus.INFO, "User is on Cart Details screen.");
			CaptureScreen();
			
			if(driver.findElementByAccessibilityId("test-CHECKOUT").isEnabled())
			{
				int rowNum=Integer.parseInt(data.get("row"));
				Result_to_Xls(xls,"TestData",rowNum,"FAIL","Result");
				test.log(LogStatus.FAIL, "Checkout should be disabled when cart is empty.");
				CaptureScreen();
			}
			
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
