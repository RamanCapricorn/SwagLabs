package com.testCases;


import org.testng.annotations.DataProvider;

import com.base.ExcelUtil;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

import com.base.BaseClass;

public class DataProviderClass extends BaseClass{

	@DataProvider(name="SwagLabs Positive Scenario")
	public static Object[][] SwagLabsPositiveScenario(){

		return ExcelUtil.getTestData(xls, "SwagLabs Positive Scenario");
	}
	
	@DataProvider(name="SwagLabs Negative Scenario")
	public static Object[][] SwagLabsNegativeScenario(){

		return ExcelUtil.getTestData(xls, "SwagLabs Negative Scenario");
	}
}
