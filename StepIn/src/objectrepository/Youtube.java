package objectrepository;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utils.AppLaunch;

public class Youtube {

	WebDriver driver = AppLaunch.driver;

	public WebElement search()

	{
		return driver.findElement(By.xpath("//button[@id='search-icon-legacy']"));
	}
	
	public WebElement searchTextbox()

	{

	 return driver.findElement(By.id("search-input"));

	}
	
	public WebElement StepIn()

	{

	 return driver.findElement(By.xpath("//h3/span[text()='STeP-IN Forum']"));

	}

	 

	public WebElement VedioTab()

	{

	 return driver.findElement(By.xpath("//*[@id='tabsContent']/paper-tab[2]/div"));

	}

}
