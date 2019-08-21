package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AppLaunch {

	public static WebDriver driver;
	public static String browser, device, appurl, os, driverPath, mode;
	public static String username, password;

	// public static final Logger log= Logger.getLogger(AppLaunch.class.getName());

	public Properties config = new Properties();
	public static HashMap<String, String> configure = new HashMap<String, String>();

	public void loadData() throws IOException {
		File file = new File(System.getProperty("user.dir") + "/configuration/config.properties");
		FileInputStream f = new FileInputStream(file);
		config.load(f);
		browser = config.getProperty("browser");
		device = config.getProperty("device");
		username = config.getProperty("username");
		password = config.getProperty("password");
		appurl = config.getProperty("appurl");
		os = config.getProperty("os");
		driverPath = config.getProperty("driverPath");
		mode = config.getProperty("mode");
	}

	public void init() throws IOException {

		loadData();
		selectBrowser(browser, device);

	}

	public void LaunchAUT() {
		getUrl(appurl);
	}

	public void selectBrowser(String browser, String device) {

		if (browser.equalsIgnoreCase("chrome")) {

			if (os.equalsIgnoreCase("Linux")) {
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/driver/chromedriver");
			}

			else
				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + "/driver/chromedriver.exe");

			Log.info("creating the object of " + browser);

			if (device.contentEquals("ipad")) {

				Map<String, Object> deviceMetrics = new HashMap<>();
				deviceMetrics.put("width", 768);
				deviceMetrics.put("height", 1024);
				// deviceMetrics.put("pixelRatio", 2);

				Map<String, Object> mobileEmulation = new HashMap<>();
				mobileEmulation.put("deviceName", "iPad");
				// mobileEmulation.put("deviceMetrics", deviceMetrics);
				// mobileEmulation.put("userAgent","Mozilla/5.0 (iPad; CPU OS 7_0 like Mac OS X)
				// AppleWebKit/537.51.1 (KHTML, like Gecko) Version/7.0 Mobile/11A465
				// Safari/9537.53");

				Map<String, Object> chromeOptions = new HashMap<>();
				chromeOptions.put("mobileEmulation", mobileEmulation);

				DesiredCapabilities capabilities = DesiredCapabilities.chrome();
				capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);

				driver = new ChromeDriver(capabilities);

			} else if (device.contentEquals("desktop")) {
				//start
				File file = new File(System.getProperty("user.dir") + "\\ExportProject");
				//System.out.println(file.toString());
				HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
				chromePrefs.put("profile.default_content_settings.popups", 0);
				chromePrefs.put("download.default_directory", file);
				ChromeOptions options = new ChromeOptions();
				options.setExperimentalOption("prefs", chromePrefs);
				DesiredCapabilities cap = DesiredCapabilities.chrome();
				cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				cap.setCapability(ChromeOptions.CAPABILITY, options);
				//end
				options.setAcceptInsecureCerts(true);
				options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				if (os.equalsIgnoreCase("Linux")) {
					options.addArguments("--headless");
					options.addArguments("--disable-gpu");
					options.addArguments("--no-sandbox");
					options.addArguments("--allow-insecure-localhost");
					//options.addArguments("window-size=1536,1100");
					options.addArguments("--start-maximized");
					// removed as aws private moved to new server - https://10.134.37.56
					/*if (!appurl.contains("10.134.37.100")) {
						appurl = "https://localhost/login";
						options.addArguments("--allow-insecure-localhost");
					}*/
					options.addArguments("window-size=1536,1100");
					driver = new ChromeDriver(options);
				} else {
					if (mode.equalsIgnoreCase("headless")) {
						options.addArguments("--headless");
						options.addArguments("--allow-insecure-localhost");
						//options.addArguments("--start-maximized");
						options.addArguments("window-size=1536,1100");

					} else {
						options.addArguments("--start-maximized");
					}
					driver = new ChromeDriver(options);
				}
			}
			System.out.println("Chrome Driver    " + driver);
		}

		else if (browser.equalsIgnoreCase("firefox")) {
			if (os.equalsIgnoreCase("windows"))
				System.setProperty("webdriver.gecko.driver",
						System.getProperty("user.dir") + "/driver/geckodriver.exe");
			Log.info("creating the object of " + browser);
			FirefoxOptions options = new FirefoxOptions();
			options.setAcceptInsecureCerts(true);
			options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);

			if (os.equalsIgnoreCase("Linux")) {
				System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/driver/geckodriver");
				options.setHeadless(true);

			}

			driver = new FirefoxDriver(options);
			System.out.println("Firefox Driver    " + driver);
		}

		else if (browser.equalsIgnoreCase("ie")) {
			System.setProperty("webdriver.ie.driver", System.getProperty("user.dir") + "/driver/IEDriverServer.exe");
			Log.info("creating the object of " + browser);
			driver = new InternetExplorerDriver();

			System.out.println("IE Driver    " + driver);
		}

		else if (browser.equalsIgnoreCase("edge")) {
			System.setProperty("webdriver.edge.driver",
					System.getProperty("user.dir") + "/src/main/resources/driver/MicrosoftWebDriver.exe");
			Log.info("creating the object of " + browser);
			driver = new EdgeDriver();

			System.out.println("IE Driver    " + driver);
		}

		else if (browser.equalsIgnoreCase("headless")) {
			driver = new HtmlUnitDriver();
		}

		else {
			System.out.println("The Browser Type is Undefined");
		}

	}

	public void getUrl(String url) {
		Log.info("launching  " + url);
		driver.get(url);
		// boolean deleted = true;
		// try {
		// // mn.map(newName);
		// //md.inputCard(IpNewCardName);
		// driver.findElement(By.id("hcl-login-form-username"));
		// deleted = true;
		// System.out.println("First launch");

		// } catch (org.openqa.selenium.NoSuchElementException e) {
		// deleted = false;
		// driver.navigate().refresh();
		// System.out.println("refreshed");
		// }
		// No refresh required.
		/*if (browser.equalsIgnoreCase("chrome")) {
			driver.navigate().refresh();
		}*/
		if (browser.equalsIgnoreCase("firefox"))
			driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

	}

	public void closeBrowser() {

		driver.quit();

	}

}
