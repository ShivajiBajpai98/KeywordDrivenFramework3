package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class Base {
    protected static final Logger logger = Logger.getLogger(Base.class);
    protected WebDriver driver;
    protected Properties properties;

    public Base()
    {
        initializeProperties("src" + System.getProperty("file.separator") + "test" + System.getProperty("file.separator") + "resources" + System.getProperty("file.separator") + "config.properties");
        initializeLogger();
    }

    public WebDriver initializeBrowser()
    {
        String browserName = properties.getProperty("browser");
        boolean headlessMode = Boolean.parseBoolean(properties.getProperty("headless"));

        if (browserName.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            if (headlessMode) {
                options.addArguments("--headless");
            }
            driver = new ChromeDriver(options);
        } else {
            throw new IllegalArgumentException("Invalid browser name: " + browserName);
        }

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        return driver;
    }

    private void initializeProperties(String filePath)
    {
        try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
            properties = new Properties();
            properties.load(fileInputStream);
        } catch (IOException e) {
            logger.error("Error initializing properties: " + e.getMessage());
            throw new RuntimeException("Error initializing properties: " + e.getMessage());
        }
    }

    private void initializeLogger() {
        PropertyConfigurator.configure("src/test/resources/log4j.properties");
    }

    public Properties getProperties() {
        return properties;
    }

    public void closeBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }
}
