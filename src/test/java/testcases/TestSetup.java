package testcases;

import base.Base;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import reusable.Reusable;
import utility.ExcelUtility;

import java.io.File;
import java.util.List;
import java.util.Properties;

public class TestSetup extends Base {
    protected Properties properties;
    protected WebDriver driver;
    protected Reusable reusable;
    protected List<String> steps;

    @BeforeClass
    public void setUp() {
        // Initialize the WebDriver and properties
        driver = initializeBrowser();
        properties = getProperties();

        // Create an instance of the Reusable class
        reusable = new Reusable(driver);

        // Read data from multiple sheets
        String[] sheetNames = {"login", "registration"};
        String filePath = "src" + File.separator + "test" + File.separator + "resources" + File.separator + "scenarios.xlsx";
        steps = ExcelUtility.readTestSteps(filePath, sheetNames);
    }

    @AfterClass
    public void tearDown() {
        // Close the browser
        reusable.closeBrowser();
    }
}
