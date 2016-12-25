package com.automation.common.utils;

import java.util.Iterator;

import org.testng.annotations.DataProvider;

public class TestDataProvider {

	@DataProvider(name = "MyTest1")
	public static Object[][] mytest1() throws Exception {
		XLSReader xlsreader = new XLSReader("./src/main/resources/testdata/MyTestData1.xlsx");
		Object[][] testData = xlsreader.readTestDataPOM();
		return testData;
	}
	
	@DataProvider(name = "MyTest2")
	public static Iterator<Object[]> mytest2() throws Exception {
		String sheetName = "UserDomain";
		String scenarioName = "Positive";
		XLSReader xlsreader = new XLSReader("./src/main/resources/testdata/MyTestData2.xlsx");
		Iterator<Object[]> testData = xlsreader.readTestData(sheetName, scenarioName);
		return testData;
	}
	
	
}
