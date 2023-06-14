package listener;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ExtentReportListener implements ITestListener {
    private ExtentReports extent;
    private ExtentTest test;

    @Override
    public void onStart(ITestContext context) {
        String reportFileName = "TestReport.html";
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(reportFileName);

        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

        extent.setSystemInfo("Author", "Shivaji Bajpai");
        extent.setSystemInfo("Employee ID", "1851");
        extent.setSystemInfo("Company", "Atmecs Inc");
    }

    @Override
    public void onTestStart(ITestResult result) {
        test = extent.createTest(result.getMethod().getMethodName());
        test.assignAuthor("Shivaji Bajpai");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.log(Status.PASS, MarkupHelper.createLabel("Test Case Passed", ExtentColor.GREEN));
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.log(Status.FAIL, MarkupHelper.createLabel("Test Case Failed", ExtentColor.RED));
        test.fail(result.getThrowable());
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }

    // Other methods omitted for brevity
}
