package utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;


import org.openqa.selenium.TakesScreenshot;

public class TestngListener implements ITestListener {

	String filepath ="";
	@Override
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		

		System.out.println("***** Error "+result.getTestClass()+"."+result.getName()+" test has failed *****");
		Log.info("***** Error \"+result.getTestClass()+\".\"+result.getName()+\" test has failed *****");
	
    	String methodName=result.getName().toString().trim();
    	takeScreenShot(methodName);
		
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		
	}
	
	public void takeScreenShot(String methodName) {
    	//get the driver
    	
    	 File scrFile = ((TakesScreenshot) AppLaunch.driver).getScreenshotAs(OutputType.FILE);
         //The below method will save the screen shot in d drive with test method name 
            try {
				String filePath = System.getProperty("user.dir")+"/screenshots/";
				FileUtils.copyFile(scrFile, new File(filePath+methodName+".png"));
				System.out.println("***Placed screen shot in "+filePath+" ***");
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

}
