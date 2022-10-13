package listeners;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import resources.BaseClass;
import utilities.ExtentReporter;

public class Listeners extends BaseClass implements ITestListener {
	WebDriver driver = null;
	ExtentReports extentReport = ExtentReporter.getExtentReport();
	ExtentTest extentTest;
	ThreadLocal<ExtentTest> extentTestThread = new ThreadLocal<>();

	@Override
	public void onTestStart(ITestResult result) {
		extentTest = extentReport.createTest(result.getName() + " execution started");
		extentTestThread.set(extentTest);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// extentTest.log(Status.PASS, "Test Passed");
		extentTestThread.get().log(Status.PASS, "Test Passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		//extentTest.fail(result.getThrowable());
		extentTestThread.get().fail(result.getThrowable());
		String testName = result.getName();
		try {
			driver = (WebDriver) result.getTestClass().getRealClass().getDeclaredField("driver")
					.get(result.getInstance());
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			String screenShotFilePath = takeScreenshot(testName, driver);
			extentTestThread.get().addScreenCaptureFromPath(screenShotFilePath,result.getName());
		} catch (IOException e ) {

			e.printStackTrace();
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {
	}

	@Override
	public void onStart(ITestContext context) {
	}

	@Override
	public void onFinish(ITestContext context) {
		extentReport.flush();
	}

}
