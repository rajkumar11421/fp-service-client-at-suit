package com.fp.service.client.util;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportListener extends TestListenerAdapter {

	public ExtentHtmlReporter htmlReporter; 
	public ExtentReports extent;
	public ExtentTest test; 
	
	private static String getTestMethodName(ITestResult iTestResult) {
		return iTestResult.getMethod().getConstructorOrMethod().getName();
	}

	@Override
	public void onStart(ITestContext iTestContext) {
		htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir")+"/test-output/fp-report.html");
		htmlReporter.config().setDocumentTitle("FP Services Automation Report");
		htmlReporter.config().setReportName("FP Services Automation Testing Report");
		htmlReporter.config().setTheme(Theme.DARK);
		
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		extent.setSystemInfo("Environment", "Test");
		
	}

	@Override
	public void onTestSuccess(ITestResult iTestResult) {
		test = extent.createTest(iTestResult.getName()); 
		
		test.log(Status.PASS, "Test case passed is "+iTestResult.getName());
	}

	@Override
	public void onTestFailure(ITestResult iTestResult) {
		test = extent.createTest(iTestResult.getName());
		
		test.log(Status.PASS, "Test case failed is "+iTestResult.getName());
		test.log(Status.PASS, "Test case failed is "+iTestResult.getThrowable());
	}

	@Override
	public void onTestSkipped(ITestResult iTestResult) {
		test = extent.createTest(iTestResult.getName());
		
		test.log(Status.SKIP, "Test case skipped is "+iTestResult.getName());
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
		System.out.println("Test failed but it is in defined success ratio " + getTestMethodName(iTestResult));
	}
	
	@Override
	public void onFinish(ITestContext iTestContext) {
		extent.flush();
	}

}
