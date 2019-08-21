package testscripts;

import java.io.IOException;

import org.apache.log4j.xml.DOMConfigurator;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import app.Login;
import extUtils.ExcelHandler;
import objectrepository.Youtube;
import utils.AppLaunch;
import utils.Log;
import utils.Synchronisation;
import utils.UiAction;

public class TestAutothon {
	AppLaunch settings = new AppLaunch();
	Synchronisation sync = new Synchronisation();

	@BeforeClass
	public void LaunchApplication() throws IOException {
		//step 1 : Open Youtube
		settings.init();
		settings.LaunchAUT();
	}


	

	@Test(priority = 1)
	public void openStepinForum() throws IOException, InterruptedException {
		
		Synchronisation sync = new Synchronisation();
		Youtube obj = new Youtube();
		
		//Step2 :- Search for step in forum
		sync.waitForElementToBeClickable(obj.searchTextbox());
		sync.wait(5);
		String data="step-inforum";
		UiAction ui= new UiAction();
		ui.moveToElementandEnter(obj.searchTextbox(), data);
		obj.search().click();
		
		//Step3 :- Open Step In Forum channel
		sync.waitForElementToBeClickable(obj.StepIn());
		obj.StepIn().click();
		
		//step4:-Navigate to videos tab
		sync.waitForElementToBeClickable(obj.VedioTab());
		obj.VedioTab().click();
		
		System.out.println("done");
		
		sync.wait(10);
		
		
		// validate application title displayed ot not?
		//Assert.assertTrue(wm.appTitle().isDisplayed());
		Log.info("Positive scenarion tested succesfully");
	}

	

	@AfterTest
	public void Exit() {
		Log.endTestCase("Login ITX");
		//settings.closeBrowser();
		System.out.println("End---> Login ITXP");
	}

}
