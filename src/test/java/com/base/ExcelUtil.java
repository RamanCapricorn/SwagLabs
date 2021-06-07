package com.base;

import java.util.Hashtable;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

public class ExcelUtil extends BaseClass{
	
public static Object[][] getTestData(Xlsx_Reader xls,String testCaseName){
		
		//Xlsx_Reader xls=new Xlsx_Reader("E:\\Selenium\\TestCaseData.xlsx");
		String SheetName="TestData";
		//String testCaseName="TestC";
		
		testStartRowNum=1;
		
		while(!xls.getCellData(SheetName, 0, testStartRowNum).equals(testCaseName))
		{
			testStartRowNum++;
		}
		colSRowNum=testStartRowNum;
		System.out.println("test starts row num:"+testStartRowNum);
		int colStartRowNum=testStartRowNum+1;
		int dataStartRowNum=testStartRowNum+2;

		//calculate rows of data
		 rows=0;
		while(!xls.getCellData(SheetName, 0, dataStartRowNum+rows).equals(""))
		{
			rows++;
		}
		
		
		//calculate total cols data
		int cols=0;
		
		while(!xls.getCellData(SheetName, cols, colStartRowNum).equals(""))
		{
			cols++;
		}
		//System.out.println(cols);
		Object[][] data= null;
		data=new Object[rows][1];
		
		Hashtable<String,String>table=null;
		
		for(int rNum=0;rNum<rows;rNum++)
		{
			table=new Hashtable<String,String>();
			for(int cNum=0;cNum<cols;cNum++)
			{
				
				String key=xls.getCellData(SheetName,cNum,colStartRowNum);
				String value=xls.getCellData(SheetName,cNum,dataStartRowNum+rNum);
//				System.out.println("Cell Value : "+value);
				table.put(key, value);
				
			}
			dataRowNum=dataStartRowNum+rNum;
			String RowNum=String.valueOf(dataRowNum);
			table.put("row", RowNum);
			data[rNum][0]=table;
			//System.out.println();
		}
		
		return data;
	}
  public static boolean isRunnable(String testName,Xlsx_Reader xls){
	  String sheet="TestCases";
	  int rows=xls.getRowCount(sheet);
	  for(int r=2;r<=rows;r++)
	  {
		  String tName=xls.getCellData(sheet, "TCID", r);
		  if(tName.equals(testName))
		  {
			  String Runmode=xls.getCellData(sheet, "Runmode", r);
			  if(Runmode.equals("Y"))
			  {
				  return true;
			  }else
			  {
				  return false;
			  }
				  
		  }
			  
	  }
	  
	return false;
   }
}


