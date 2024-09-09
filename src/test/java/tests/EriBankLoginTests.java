package tests;

import helpers.Helpers;
import helpers.PropertiesReader;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.InteractsWithApps;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import static java.time.Duration.ofSeconds;

public class EriBankLoginTests {

    protected ThreadLocal<AppiumDriver> driver = new ThreadLocal<>();
    protected ThreadLocal<WebDriverWait> wait = new ThreadLocal<>();
    protected ThreadLocal<DesiredCapabilities> desiredCapabilities = new ThreadLocal<>();

    protected ThreadLocal<Helpers> helper = new ThreadLocal<>();

    public AppiumDriver getDriver() {
        return driver.get();
    }

    public WebDriverWait getWait() {
        return wait.get();
    }

    public Helpers getHelper() {
        return helper.get();
    }

    String captureLevel = null;

    @BeforeMethod
    public void setUp(@Optional ITestContext context, @Optional Method method) throws MalformedURLException {

        String applicationType = context.getCurrentXmlTest().getParameter("application.type");
        captureLevel = context.getSuite().getParameter("capture.level");

        DesiredCapabilities caps = new DesiredCapabilities();

        caps.setCapability("testName", method.getName());
        caps.setCapability("accessKey", new PropertiesReader().getProperty("ct.accessKey"));
        caps.setCapability("automationName", "UIAutomator2");
        caps.setCapability("deviceQuery", "@os='android' and contains(@name, 'Galaxy')");

        if (applicationType.equalsIgnoreCase("unprotected")) {

            caps.setCapability("app", "cloud:uniqueName=StandardApp_CTAppSecIntegration");
            caps.setCapability("appPackage", "com.experitest.ExperiBank");
            caps.setCapability("appActivity", ".LoginActivity");

        } else if (applicationType.equalsIgnoreCase("protected")) {

            caps.setCapability("app", "cloud:uniqueName=ProtectedApp_CTDetectedAsThreat");
            caps.setCapability("appPackage", "com.experitest.ProtectedExperiBank");
            caps.setCapability("appActivity", ".LoginActivity");

        } else if (applicationType.equalsIgnoreCase("protectedCustomToast")) {

            caps.setCapability("app", "cloud:uniqueName=ProtectedApp_CustomToast");
            caps.setCapability("appPackage", "com.experitest.ProtectedExperiBank");
            caps.setCapability("appActivity", ".LoginActivity");

        } else if (applicationType.equalsIgnoreCase("protectedWithoutServer")) {

            caps.setCapability("app", "cloud:uniqueName=ProtectedApp_WithoutServer");
            caps.setCapability("appPackage", "com.experitest.ProtectedExperiBank");
            caps.setCapability("appActivity", ".LoginActivity");

        }

        caps.setCapability("fullReset", true);
        caps.setCapability("attachCrashLogToReport", true);

        desiredCapabilities.set(caps);
        driver.set(new AndroidDriver(new URL(new PropertiesReader().getProperty("ct.cloudUrl") + "/wd/hub"), caps));
        wait.set(new WebDriverWait(getDriver(), ofSeconds(10)));
        helper.set(new Helpers(getDriver()));
    }

    @Test
    public void login_test_UNPROTECTED() {
        System.out.println("Unprotected App - Login Test");

        getWait().until(ExpectedConditions.elementToBeClickable(By.id("com.experitest.ExperiBank:id/usernameTextField")));

        getHelper().startCapturePerformanceMetrics("4G-average", captureLevel, "com.experitest.ExperiBank");

        getDriver().findElement(By.id("com.experitest.ExperiBank:id/usernameTextField")).sendKeys("company");
        getDriver().findElement(By.id("com.experitest.ExperiBank:id/passwordTextField")).sendKeys("company");
        getDriver().findElement(By.id("com.experitest.ExperiBank:id/loginButton")).click();
        getWait().until(ExpectedConditions.elementToBeClickable(By.id("com.experitest.ExperiBank:id/makePaymentButton")));

        getHelper().endCapturePerformanceMetrics("login_test_UNPROTECTED");
    }

    @Test
    public void login_test_PROTECTED() {
        System.out.println("Protected App - Login Test");

        getWait().until(ExpectedConditions.elementToBeClickable(By.id("com.experitest.ProtectedExperiBank:id/usernameTextField")));

        getHelper().startCapturePerformanceMetrics("4G-average", captureLevel, "com.experitest.ProtectedExperiBank");

        getDriver().findElement(By.id("com.experitest.ProtectedExperiBank:id/usernameTextField")).sendKeys("company");
        getDriver().findElement(By.id("com.experitest.ProtectedExperiBank:id/passwordTextField")).sendKeys("company");
        getDriver().findElement(By.id("com.experitest.ProtectedExperiBank:id/loginButton")).click();
        getWait().until(ExpectedConditions.elementToBeClickable(By.id("com.experitest.ProtectedExperiBank:id/makePaymentButton")));

        getHelper().endCapturePerformanceMetrics("login_test_PROTECTED");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {

        if (result.isSuccess()) {
            getHelper().setReportStatus("Passed", "Test Passed");
        } else if (!result.isSuccess()) {
            getHelper().setReportStatus("Failed", "Test Failed");
        }

        if (driver != null) {
            getDriver().quit();
            driver.remove();
            wait.remove();
        }
    }

}
