package com.automation.common.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class Base {

	public static ExtentReports extentReports;
	public ExtentTest reportLogger;
	public String browser_type;
	public WebDriver wd;
	public String tcid;
	public String iteration;
	
	@BeforeSuite(groups={"BVT","QE", "UAT", "Reg"})
	public void create_Report(){
		
		extentReports = new ExtentReports("./log/Report/Application_"+get_datetimestamp() +".html",false);
		
	}
	
	@Parameters({"browser"})
	@BeforeMethod(groups={"BVT","QE", "UAT", "Reg"})
	public void launchApp(String btype) throws Exception{
		browser_type=btype;
		if(btype.equals("ff")){			
			wd=new FirefoxDriver();
		}else if(btype.equals("ch")){
			System.setProperty("webdriver.chrome.driver", "./src/main/resources/drivers/chromedriver.exe");
			wd=new ChromeDriver();
		}else if(btype.equals("ie")){
			System.setProperty("webdriver.ie.driver", "./src/main/resources/drivers/IEDriverServer.exe");
			wd=new InternetExplorerDriver();
			
		}
		wd.get(LoadConstants.getPropertyValue("APP_URL"));
		wd.manage().window().maximize();
		wd.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		
	}
	
	@AfterMethod(groups={"BVT","QE", "UAT", "Reg"})
	public void cloaseApp(){	
		wd.close();
	}
	
	@AfterSuite(groups={"BVT","QE", "UAT", "Reg"})
	public void generateReport(){	
		
		extentReports.endTest(reportLogger);
		extentReports.flush();
		
	}
	
	//	capture snapshot
	public String getScreenshot() throws Exception{
		
		TakesScreenshot sc=(TakesScreenshot)wd;
		File screenshotAs = sc.getScreenshotAs(OutputType.FILE);
		
		String fpath = "./log/screenshots/" + tcid + "_" + get_datetimestamp() +".png";
		String fpath2 = "./log/Report/log/screenshots/" + tcid + "_" + get_datetimestamp() +".png";
		FileUtils.copyFile(screenshotAs, new File(fpath2).getCanonicalFile());
		return fpath;
				
	}
	
	public String get_datetimestamp(){
		Date date = new Date();
		//	format date
		 SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy hh-mm-ss");
		 String format = dateFormat.format(date);
		 return format;
	}
	
}
