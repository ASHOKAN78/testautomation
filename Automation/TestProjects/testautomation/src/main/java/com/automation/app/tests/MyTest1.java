package com.automation.app.tests;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import com.automation.common.utils.Base;
import com.automation.common.utils.TestDataProvider;
import com.relevantcodes.extentreports.LogStatus;

public class MyTest1 extends Base{
	
	Logger logger = Logger.getLogger(MyTest1.class);
	
	@Test(dataProvider="MyTest1", dataProviderClass=TestDataProvider.class, groups={"BVT","Reg"})
	public void mytest1(HashMap<String, HashMap<String, String>> testData) throws Exception {
		
		tcid = testData.get("TestCaseName").get("AutomationTestCaseID");
		logger.info("Test Execution started for " + tcid);
		reportLogger = extentReports.startTest(tcid+"_" + browser_type);
		reportLogger.log(LogStatus.INFO, "Test Data", testData.toString());
		reportLogger.log(LogStatus.PASS, "Validate Login", "Passed - Successfull Login");
		reportLogger.log(LogStatus.PASS, "Create Data Domain", "Passed - Data Domain created successfully");
		reportLogger.log(LogStatus.FAIL, "Create User Domain", "Passed - User Domain created successfully" + reportLogger.addScreenCapture(getScreenshot()));
		logger.info(testData.toString());
		logger.info("Test Execution completed for the test case : " + tcid);
		
	}

}
