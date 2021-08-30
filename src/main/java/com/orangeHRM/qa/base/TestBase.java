package com.orangeHRM.qa.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import com.orangeHRM.qa.utils.TestUtil;
import com.orangeHRM.qa.utils.WebEventListener;

public class TestBase {

	public static Properties properties;
	public static FileInputStream fileInputStream;
	public static WebDriver webDriver;
	public static WebEventListener webEventListener;
	public static EventFiringWebDriver eventFiringWebDriver;

	public TestBase() {
		try {
			fileInputStream = new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\java\\com\\orangeHRM\\qa\\config\\config.properties");
			properties = new Properties();
			properties.load(fileInputStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void initialize() {
		String browser = properties.getProperty("browser");
		if(browser.contains("chrome")) {
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\src\\main\\java\\com\\orangeHRM\\qa\\config\\chromedriver.exe");
			ChromeOptions chromeOptions = new ChromeOptions();
			if(browser.contains("headless")) 
				chromeOptions.addArguments("headless");
			webDriver = new ChromeDriver(chromeOptions);
		}
		if(browser.contains("edge")) {
			System.setProperty("webdriver.edge.driver", System.getProperty("user.dir") + "\\src\\main\\java\\com\\orangeHRM\\qa\\config\\msedgedriver.exe");
			webDriver = new EdgeDriver();
		}
		eventFiringWebDriver = new EventFiringWebDriver(webDriver);
		webEventListener = new WebEventListener();
		eventFiringWebDriver.register(webEventListener);
		webDriver = eventFiringWebDriver;
		
		webDriver.manage().window().maximize();
		webDriver.manage().deleteAllCookies();
		webDriver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
		webDriver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT, TimeUnit.SECONDS);
		webDriver.get(properties.getProperty("url"));
	}

}
