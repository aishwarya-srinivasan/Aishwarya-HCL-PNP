package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class ControlValidation {

	WebDriver driver = AppLaunch.driver;
	UiAction act = new UiAction();
	Synchronisation sync=new Synchronisation();
	public void validateWebElement(String action, String type, String identifier, String data) throws InterruptedException
	{
		WebElement element = driver.findElement(By.id(identifier));
		boolean status =false;
		Synchronisation sync=new Synchronisation();

		switch(action)
		{
		case "change":
		{
			switch(type)
			{
			case "switch":
				//element.click();
				JavascriptExecutor js = (JavascriptExecutor)driver;
				js.executeScript("arguments[0].click();", element);
				break;

			case "dropdown":
				act.selecText(element, data);
				sync.WaitInMs(500);
				break;

			case "text":
				element.clear();
				element.sendKeys(data);
				break;
				
			case "radio":
				element.click();
				break;

			}
			break;
		}

		case "check":
			switch(type)
			{
			case "switch":
				if(data.equalsIgnoreCase("on")){
					Assert.assertTrue(element.isSelected());
				}
				else
				{
					Assert.assertFalse(element.isSelected());
				}
				break;

			case "dropdown":
				Assert.assertEquals(act.selectedItem(element), data);
				break;

			case "text":
				Assert.assertEquals(element.getAttribute("value"), data);
				break;

			case "radio":
				if(data.equalsIgnoreCase("check"))
					Assert.assertTrue(element.isSelected());
				else
					Assert.assertFalse(element.isSelected());
			}
			break;
			
		case "disabled":
			Assert.assertFalse(element.isDisplayed());

		}
	}
	

}
