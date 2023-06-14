package runner;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.TestNG;

import listener.ExtentReportListener;
import testcases.LoginTest;

public class TestNGRunner {
    private static final Logger log = Logger.getLogger(TestNGRunner.class);

    public static void main(String[] args) {
        PropertyConfigurator.configure("log4j.properties");

        try {
            TestNG testNG = new TestNG();
            testNG.setTestClasses(new Class[]{LoginTest.class});
            testNG.addListener(new ExtentReportListener()); // Add Extent Report listener
            testNG.run();
        } catch (Exception e) {
            log.error("Test execution failed: " + e.getMessage());
        }
    }
}
