package com.configuration;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BrowserEnvironment {
    private String browserName = "chrome";
    private boolean headlessBrowser;
    private int webElementTimeOut = 20;
    private static Logger log = LoggerFactory.getLogger(BrowserEnvironment.class);
    private WebDriver driver;

    public BrowserEnvironment() {
        this.headlessBrowser = PropertiesStore.BROWSER_HEADLESS.isSpecified() && PropertiesStore.BROWSER_HEADLESS.getBooleanValue();
        this.webElementTimeOut = PropertiesStore.BROWSER_WEBELEMENT_TIMEOUT.isSpecified() ?
                PropertiesStore.BROWSER_WEBELEMENT_TIMEOUT.getIntValue() : this.webElementTimeOut;
        this.browserName = PropertiesStore.BROWSER.isSpecified() ? PropertiesStore.BROWSER.getValue() : this.browserName;
        this.initBrowserSetting();
        log.info("Browser name: " + browserName);
        log.info("WebElement TimeOut: " + webElementTimeOut);
        log.info("Is headless browser: " + headlessBrowser);
        log.info("----------------------------------");
    }

    private void initBrowserSetting() {
        this.webElementTimeOut = PropertiesStore.BROWSER_WEBELEMENT_TIMEOUT.isSpecified() ? PropertiesStore.BROWSER_WEBELEMENT_TIMEOUT.getIntValue() : this.webElementTimeOut;
        this.headlessBrowser = PropertiesStore.BROWSER_HEADLESS.getBooleanValue();
    }

    public WebDriver getDriver() {
        WebDriver driver;
        switch (this.browserName) {
            case "chrome":
                ChromeOptions optionsChrome = new ChromeOptions();
                WebDriverManager.chromedriver().setup();
                optionsChrome.addArguments("start-maximized");
                driver = new ChromeDriver(optionsChrome);
                break;
            case "firefox":
                FirefoxOptions optionsFirefox = new FirefoxOptions();
                WebDriverManager.firefoxdriver().setup();
                optionsFirefox.addArguments("start-maximized");
                driver = new FirefoxDriver(optionsFirefox);
                break;
            default:
                InternetExplorerOptions optionsDefault = new InternetExplorerOptions();
                WebDriverManager.iedriver().setup();
                driver = new InternetExplorerDriver(optionsDefault);
        }
        driver.get(System.getProperty("appUrl"));
        this.driver = driver;
        return this.driver;
    }
}