package pl.moderntester.pages.configuration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class BasePage {
    public WebDriver driver;
    private static Logger log = LoggerFactory.getLogger(BasePage.class);

    public BasePage() {
    }

    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public enum Continents {
        ASIA, AFRICA, EUROPE, ANTARCTICA, NORTH_AMERICA, SOUTH_AMERICA, AUSTRALIA;
    }

    public void doScreenShot(String fileName) {
        TakesScreenshot screenshot = ((TakesScreenshot) driver);
        File srcFile = screenshot.getScreenshotAs(OutputType.FILE);
        File destFile = new File("src/main/resources/files/" + fileName + ".png");
        try {
            FileUtils.copyFile(srcFile, destFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("Screen shot taken");
    }

    public WebElement clickRandomElement(List<WebElement> list) {
        return list.get(new Random().nextInt(list.size()));
    }

    public void selectRandomElement(Select selectList) {
        selectList.selectByIndex(new Random().nextInt(selectList.getOptions().size()));
    }

    public Dimension getWindowSize() {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        int width = Integer.parseInt(String.valueOf(jse.executeScript("return window.innerWidth")));
        int height = Integer.parseInt(String.valueOf(jse.executeScript("return window.innerHeight")));
        return new Dimension(width, height);
    }

    public Dimension getElementSize(WebElement element) {
        int height = Integer.parseInt(element.getCssValue("height")
                .replace("px", ""));
        int width = Integer.parseInt(element.getCssValue("width")
                .replace("px", ""));
        return new Dimension(width, height);
    }

    public WebElement findOptionByText(List<WebElement> optionsList, String optionName){
        return optionsList.stream()
                .filter(opt -> opt.getText().equals(optionName))
                .reduce((f, s) -> s)
                .orElseThrow(() -> new RuntimeException("Can't find option"));
    }
}