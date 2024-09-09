package helpers;

import io.appium.java_client.AppiumDriver;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Helpers {

    protected AppiumDriver driver;

    public Helpers() {}

    public Helpers(AppiumDriver driver) {
        this.driver = driver;
    }

    public void startCapturePerformanceMetrics(String nvProfile, String captureLevel, String applicationName) {
        try {
            if (captureLevel.equalsIgnoreCase("Device")) {
                driver.executeScript("seetest:client.startPerformanceTransaction(\"" + nvProfile + "\")");
            } else if (captureLevel.equalsIgnoreCase("Application")) {
                driver.executeScript("seetest:client.startPerformanceTransactionForApplication(\"" + applicationName + "\", \"" + nvProfile + "\")");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Could not start Capturing. Accepted Values: [Device, Application]");
        }
    }

    public String endCapturePerformanceMetrics(String transactionName) {
        Object transaction = driver.executeScript("seetest:client.endPerformanceTransaction(\"" + transactionName + "\")");
        return transaction.toString();
    }

    public void startGroupingOfSteps(String testName) {
        driver.executeScript("seetest:client.startStepsGroup(\"" + testName + "\")");
    }

    public void endGroupingOfSteps() {
        driver.executeScript("seetest:client.stopStepsGroup()");
    }

    public void addPropertyForReporting(String property, String value) {
        driver.executeScript("seetest:client.addTestProperty(\"" + property + "\", \"" + value + "\")");
    }

    // Values accepted: true, false
    public void addReportStep(String input, String status) {
        driver.executeScript("seetest:client.report(\"" + input + "\", \"" + status + "\")");
    }

    // Values accepted: Failed, Skipped, Passed
    public void setReportStatus(String status, String message) {
        driver.executeScript("seetest:client.setReportStatus(\"" + status + "\", \"" + status + "\", \"" + message + "\")");
    }

    public String getCurrentDateAndTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
        String currentDateAndTime = now.format(formatter);
        return currentDateAndTime;
    }

}
