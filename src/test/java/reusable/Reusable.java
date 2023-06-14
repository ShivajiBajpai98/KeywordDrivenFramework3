package reusable;

import base.Base;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

public class Reusable extends Base {
    private static final Logger log = Logger.getLogger(Reusable.class);
    private WebDriverWait wait;

    public Reusable(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PropertyConfigurator.configure("log4j.properties");
    }

    public void openURL(String url) {
        log.info("Opening URL: " + url);
        driver.get(url);
    }

    public void clickElement(By locator) {
        log.info("Clicking element: " + locator);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.click();
    }

    public void enterText(By locator, String text) {
        log.info("Entering text: " + text + ", into element: " + locator);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
        element.sendKeys(text);
    }

    public void selectOption(By locator, String optionText) {
        log.info("Selecting option: " + optionText + ", from element: " + locator);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        // Code to select the desired option from the dropdown or multi-select list
    }

    public boolean verifyElementPresent(By locator) {
        log.info("Verifying element presence: " + locator);
        return driver.findElements(locator).size() > 0;
    }

    public boolean verifyText(By locator, String expectedText) {
        log.info("Verifying text: " + expectedText + ", for element: " + locator);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        String actualText = element.getText();
        return actualText.equals(expectedText);
    }

    public void waitForElement(By locator) {
        log.info("Waiting for element: " + locator);
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void submitForm(By locator) {
        log.info("Submitting form with element: " + locator);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.submit();
    }

    public void navigateBack() {
        log.info("Navigating back");
        driver.navigate().back();
    }

    public void navigateForward() {
        log.info("Navigating forward");
        driver.navigate().forward();
    }

    public void waitForPageLoad() {
        log.info("Waiting for page to load");
        wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    }

    public void scrollToElement(By locator) {
        log.info("Scrolling to element: " + locator);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void checkCheckbox(By locator) {
        log.info("Checking checkbox: " + locator);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        if (!element.isSelected()) {
            element.click();
        }
    }

    public void clearText(By locator) {
        log.info("Clearing text in element: " + locator);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.clear();
    }

    public void switchToFrame(By locator) {
        log.info("Switching to frame: " + locator);
        WebElement frameElement = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        driver.switchTo().frame(frameElement);
    }

    public void switchToWindow(String windowHandle) {
        log.info("Switching to window: " + windowHandle);
        driver.switchTo().window(windowHandle);
    }

    public void executeJavaScript(String script) {
        log.info("Executing JavaScript: " + script);
        ((JavascriptExecutor) driver).executeScript(script);
    }

    public void wait(int seconds) {
        log.info("Waiting for " + seconds + " seconds");
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void assertCondition(boolean condition) {
        log.info("Asserting condition: " + condition);
        Assert.assertTrue(condition);
    }

    public void takeScreenshot(String filename) {
        log.info("Taking screenshot: " + filename);
        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenshotFile, new File(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public By getLocator(String locatorType, String locatorValue) {
        switch (locatorType.toLowerCase()) {
            case "id":
                return By.id(locatorValue);
            case "name":
                return By.name(locatorValue);
            case "classname":
                return By.className(locatorValue);
            case "tagname":
                return By.tagName(locatorValue);
            case "linktext":
                return By.linkText(locatorValue);
            case "partiallinktext":
                return By.partialLinkText(locatorValue);
            case "cssselector":
                return By.cssSelector(locatorValue);
            case "xpath":
                return By.xpath(locatorValue);
            default:
                throw new IllegalArgumentException("Invalid locator type: " + locatorType);
        }
    }
}
