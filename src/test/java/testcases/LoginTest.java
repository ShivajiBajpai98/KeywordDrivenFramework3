package testcases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;

public class LoginTest extends TestSetup {
    private static final Logger logger = LogManager.getLogger(LoginTest.class);

    @Test
    public void loginTest() {
        try {
            for (String step : steps) {
                logger.info("Executing test step: " + step);
                executeTestStep(step);
            }
        } catch (Exception e) {
            logger.error("An exception occurred during test execution:", e);
            reusable.takeScreenshot("error_screenshot.png");
        }
    }

    private void executeTestStep(String step) {
        switch (step) {
            case "openbrowser":
                reusable.openURL(properties.getProperty("baseURL"));
                break;
            case "enterusername":
                reusable.waitForPageLoad();
                reusable.enterText(reusable.getLocator(properties.getProperty("usernameLocatorType"), properties.getProperty("usernameLocator")), properties.getProperty("username"));
                break;
            case "enterpassword":
                reusable.waitForPageLoad();
                reusable.enterText(reusable.getLocator(properties.getProperty("passwordLocatorType"), properties.getProperty("passwordLocator")), properties.getProperty("password"));
                break;
            case "clickelement":
                reusable.waitForPageLoad();
                reusable.clickElement(reusable.getLocator(properties.getProperty("loginButtonLocatorType"), properties.getProperty("loginButtonLocator")));
                break;
            case "waitForElement":
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
                wait.until(ExpectedConditions.visibilityOfElementLocated(reusable.getLocator(properties.getProperty("dashboardLocatorType"), properties.getProperty("dashboardLocator"))));
                break;
            case "closebrowser":
                reusable.closeBrowser();
                break;
            default:
                // Handle invalid step
                //System.out.println("Invalid step: " + step);
                break;
        }
    }
}
