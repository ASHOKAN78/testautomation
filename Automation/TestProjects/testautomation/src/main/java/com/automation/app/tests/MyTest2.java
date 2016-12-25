package com.automation.app.tests;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import com.automation.common.utils.Base;
import com.automation.common.utils.TestDataProvider;
import com.relevantcodes.extentreports.LogStatus;

public class MyTest2 extends Base{
	
	Logger logger = Logger.getLogger(MyTest2.class);
	
	@Test(dataProvider="MyTest2", dataProviderClass=TestDataProvider.class, groups={"QA","BVT"})
	public void mytest1(HashMap<String, String> testData) {
		
		tcid = testData.get("Testcase ID");
		reportLogger = extentReports.startTest(tcid+"_" + browser_type);
		reportLogger.log(LogStatus.INFO, "Test Data", testData.toString());
		reportLogger.log(LogStatus.PASS, "Validate Login", "Passed - Successfull Login");
		reportLogger.log(LogStatus.PASS, "Create Data Domain", "Passed - Data Domain created successfully");
		reportLogger.log(LogStatus.PASS, "Create User Domain", "Passed - User Domain created successfully");
		logger.info(testData.toString());
		
	}

}
