package utils;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import com.google.common.base.*;


public class Synchronisation {

	WebDriver driver = AppLaunch.driver;
	final int timeOut = 10;

	public void waitForElementToBecomeVisible(WebElement element) {
		WebDriverWait wait = new WebDriverWait(AppLaunch.driver, timeOut);
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	public void waitForElementToBeClickable(WebElement element) {
		WebDriverWait wait = new WebDriverWait(AppLaunch.driver, timeOut);
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	public void waitforElementToBecomeInvisible(WebElement element)
	{
		WebDriverWait wait = new WebDriverWait(AppLaunch.driver, timeOut);
		wait.until(ExpectedConditions.invisibilityOf(element));
	}

	public void waitforSelection(WebElement element)
	{
		WebDriverWait wait = new WebDriverWait(AppLaunch.driver, timeOut);
		wait.until(ExpectedConditions.elementToBeSelected(element));
	}

	public void waitforTextToAppear(WebElement element, String text)
	{
		WebDriverWait wait = new WebDriverWait(AppLaunch.driver, timeOut);
		//wait.until(ExpectedConditions.textToBePresentInElementValue(element,value));
		wait.until(ExpectedConditions.textToBePresentInElement(element, text));
	}

	public void waitforTextToAppear(WebElement element, String text, int time)
	{
		WebDriverWait wait = new WebDriverWait(AppLaunch.driver, time);
		wait.until(ExpectedConditions.textToBePresentInElement(element, text));
	}
	public void waitforElementTogetSelected(WebElement element)
	{
		WebDriverWait wait = new WebDriverWait(AppLaunch.driver, timeOut);
		wait.until(ExpectedConditions.elementSelectionStateToBe(element,true));
	}

	public void waitforAlert(int timeOutInSeconds)
	{
		WebDriverWait wait = new WebDriverWait(AppLaunch.driver, timeOut);
		wait.until(ExpectedConditions.alertIsPresent());

	}


	/*public void pageLoadWait()
	{
		Log.info("Waiting for the workspace to Refresh");
		WebDriverWait wait = new WebDriverWait(AppLaunch.driver, 600);
		//HomePage hp = new HomePage();
		//wait.until(ExpectedConditions.invisibilityOf(hp.spinner()));
		try {
			wait.until(ExpectedConditions.attributeToBe(hp.spinner(), "display", "none"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
			try {
				wait(5);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}*/


	public void wait(int timeInSec) throws InterruptedException
	{
		int time = timeInSec*1000;
		try {
			Thread.sleep(time);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void implicitWait(int time)
	{
		AppLaunch.driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
	}

	public void waittoAvoidStaleRef()
	{

		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(30,TimeUnit.SECONDS)
				.pollingEvery(5, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class)	
				.ignoring(StaleElementReferenceException.class);



		WebElement foo = (WebElement) wait.until(new Function() {    
			public WebElement apply(WebDriver driver) {    
				return driver.findElement(By.id("foo"));    
			}

			@Override
			public Object apply(Object arg0) {
				// TODO Auto-generated method stub
				return null;
			}
		});
	}
	
	public void waitForElementToBecomeVisible(WebElement element, int timeout) {
		WebDriverWait wait = new WebDriverWait(AppLaunch.driver, timeout);
		wait.until(ExpectedConditions.visibilityOf(element));
	}
	
	public void waitForElementToBeClickable(WebElement element, int timeout) {
		WebDriverWait wait = new WebDriverWait(AppLaunch.driver, timeout);
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}
	
	public void WaitInMs(int time)
    {
        try {
            Thread.sleep(time);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


}

