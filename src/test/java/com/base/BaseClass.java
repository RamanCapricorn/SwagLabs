package com.base;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import com.pageObjects.homePage;
//import com.aventstack.extentreports.ExtentReports;
//import com.aventstack.extentreports.ExtentTest;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.utilities.ExtentManager;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class BaseClass{

	//	public static WebDriver driver;
	public AppiumDriver<MobileElement> driver;

	public Properties prop;
	public static Properties prop1;
	static File file;

	public Properties Path;
	public static String ExtentReportName;

	public static int testStartRowNum;
	public static int dataRowNum;
	public static String testCaseName;
	public static String TC_Name;
	public static String propertiesFile;

	public static ExtentReports rep = ExtentManager.getInstance();
	public static ExtentTest test;

	public static int colSRowNum;
	public static String str1, str2, str3;
	public static int rows;
	public static String TestStatus = "";

	public static Xlsx_Reader xls = new Xlsx_Reader(System.getProperty("user.dir") + "\\Testdata\\TestCaseData.xlsx");

	public void TestBeforeMethod() throws AWTException, InterruptedException {
		try {
			
			DesiredCapabilities caps = new DesiredCapabilities();
			//			caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "ANDROID");
			//			caps.setCapability(CapabilityType.PLATFORM_NAME, "ANDROID");
			caps.setCapability("platformName", "ANDROID");
			caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, "9");
			caps.setCapability(MobileCapabilityType.DEVICE_NAME, "Honor 9 Lite");
			caps.setCapability(MobileCapabilityType.UDID, "S6L3Y18622006181");
			//			caps.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 60);
			caps.setCapability(MobileCapabilityType.APP, System.getProperty("user.dir")+ "\\apps\\Android.SauceLabs.Mobile.Sample.app.2.7.1.apk");
			//			caps.setCapability(MobileCapabilityType.BROWSER_NAME, "Chrome");

			//			caps.setCapability("appPackage", "com.swaglabsmobileapp");
			//			caps.setCapability("appActivity", "com.swaglabsmobileapp.MainActivity");
			caps.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.swaglabsmobileapp");
			caps.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, "com.swaglabsmobileapp.MainActivity");

			URL url = new URL("http://127.0.0.1:4723/wd/hub");

			driver = new AppiumDriver<MobileElement>(url,caps);

			System.out.println("Application Started...");

		} catch (MalformedURLException e) {
			System.out.println("Cause is: "+ e.getCause());
			System.out.println("Message is: "+ e.getMessage());
			e.printStackTrace();
		}
	}

	public void TestAfterMethod() {
		rep.endTest(test);
		rep.flush();
		driver.close();
		driver.quit();
	}

	public boolean scrollToElement (By by) throws Exception {
		boolean isFoundTheElement = driver.findElements(by).size() > 0;
		while (isFoundTheElement == false) {
			swipeVertical(0.8, 0.1, 0.5, 2000);
			isFoundTheElement = driver.findElements(by).size() > 0;
		}

		return isFoundTheElement;
	}
	
	public boolean scrollToElement (String ele) throws Exception {
		boolean isFoundTheElement = driver.findElementsByAccessibilityId(ele).size() > 0;
		while (isFoundTheElement == false) {
			swipeVertical(0.8, 0.1, 0.5, 2000);
			isFoundTheElement = driver.findElementsByAccessibilityId(ele).size() > 0;
		}

		return isFoundTheElement;
	}

	public void swipeVertical (
			double startPercentage, double finalPercentage, double anchorPercentage, int duration)
					throws Exception {
		org.openqa.selenium.Dimension size = driver.manage().window().getSize();
		int anchor = (int) (size.width * anchorPercentage);
		int startPoint = (int) (size.height * startPercentage);
		int endPoint = (int) (size.height * finalPercentage);
		getTouchAction().press(PointOption.point(anchor, startPoint))
		.waitAction(WaitOptions.waitOptions(Duration.ofMillis(duration)))
		.moveTo(PointOption.point(anchor, endPoint)).release().perform();
	}

	public TouchAction getTouchAction () {
		return new TouchAction(driver);
	}

	public String GetPropValue(String PName) {
		String x = null;
		try {
			if (prop == null) {
				prop = new Properties();
				FileInputStream fs = new FileInputStream(
						System.getProperty("user.dir") + "\\Configuration\\Config.properties");
				prop.load(fs);
			}
			x = prop.getProperty(PName);
			// System.out.println(x);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return x;
	}

	public static int RowsCount() {
		return rows;
	}

	//	public String GetObjectPath(String PName) {
	//		String P = null;
	//		try {
	//			if (Path == null) {
	//				Path = new Properties();
	//				FileInputStream fs = new FileInputStream(
	//						System.getProperty("user.dir") + "\\Configuration\\"+ propertiesFile +".properties");
	//				Path.load(fs);
	//			}
	//			P = Path.getProperty(PName);
	//			//System.out.println(P);
	//		} catch (Exception e) {
	//			e.printStackTrace();
	//		}
	//		return P;
	//	}


	public String getCurrentDate() {
		DateFormat dateformat = new SimpleDateFormat("MM/dd/yyyy");
		Date currentdate = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(currentdate);

		String today = dateformat.format(currentdate);

		// c.add(Calendar.DATE, -1);
		// Date prevdate = c.getTime();

		return today;
	}

	/*-----------------------------------------------------------------------*/

	/************************ Navigate to application *************************/
	public void navigate(String URL) {

		driver.get(GetPropValue(URL));
	}

	/*----------------------------Start the Extent Report------------------------------------*/
	public static void StartReport(String Tname,String data){
		//test=rep.startTest(Tname);
		test= rep.startTest(Tname, data);
		test.log(LogStatus.INFO , "Starting the testcase "+Tname +" :: "+data);	
		//System.out.println("Starting the testcase "+Tname + " with TFS ID "+ data);
	}
	public static void isTestRunnable(Hashtable<String,String> data){
		if(!ExcelUtil.isRunnable(testCaseName, xls) || data.get("Runmode").equals("N")){
			//xls.setCellData("TestData","Result",dataRowNum,testStartRowNum, "SKIP");
			test.log(LogStatus.SKIP,"Skipping the test as runmode is N");
			throw new SkipException("Skipping the test as runmode is N");
		}	
	}
	/************************Set Result to Excel************************/

	public static void Result_to_Xls(Xlsx_Reader xls,String SheetName,int RowNum,String Result,String Result_columnname){

		xls.setCellData(SheetName,Result_columnname,RowNum, Result,colSRowNum);
	}


	/******************Reporting*****************************/

	public static void reportPass(String Msg){
		test.log(LogStatus.PASS, Msg);
	}

	public static void reportFail(String Msg){
		test.log(LogStatus.FAIL, Msg);
	}

	public static void reportSkip(String Msg){
		test.log(LogStatus.SKIP, Msg);
	}

	public  void CaptureScreen()
	{
		Date d=new Date();
		String filename=testCaseName + "_" + d.toString().replace(":","_").replace(" ","_")+".jpg";

		String HtmlPath = "../ExtentReports/ScreenShots/"+filename;
		String ImagePath = "./ExtentReports/ScreenShots/"+filename;
		//System.out.println(Path);

		TakesScreenshot oScn = (TakesScreenshot) driver;
		File oScnShot = oScn.getScreenshotAs(OutputType.FILE);
		File oDest = new File(ImagePath);
		try {
			FileUtils.copyFile(oScnShot, oDest);
		} catch (IOException e) {System.out.println(e.getMessage());}

		test.log(LogStatus.INFO, "ScreenShot->"+ test.addScreenCapture(HtmlPath));
	}

	public void ImplicitWait() {
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
		// driver.manage().timeouts().implicitlyWait(timeSpan.FromSeconds(5));
	}

	public void turnOffImplicitWaits() {
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
	}

}
