package utils;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReport {
    static ExtentReports extent;
    static ExtentTest test;

    public static ExtentReports getReportInstance() {
        if (extent == null) {
        	ExtentSparkReporter htmlReporter = new ExtentSparkReporter("test-output/OrangeHRMReport.html");
            htmlReporter.config().setReportName("OrangeHRM Automation Test Report");
            htmlReporter.config().setDocumentTitle("Automation Report");

            extent = new ExtentReports();
            extent.attachReporter(htmlReporter);
        }
        return extent;
    }
}
