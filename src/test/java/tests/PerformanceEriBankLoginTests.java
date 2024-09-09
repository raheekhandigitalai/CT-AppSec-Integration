package tests;

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
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import static java.time.Duration.ofSeconds;

public class PerformanceEriBankLoginTests {

    protected ThreadLocal<AppiumDriver> driver = new ThreadLocal<>();
    protected ThreadLocal<WebDriverWait> wait = new ThreadLocal<>();
    protected ThreadLocal<DesiredCapabilities> desiredCapabilities = new ThreadLocal<>();

    public AppiumDriver getDriver() {
        return driver.get();
    }

    public WebDriverWait getWait() {
        return wait.get();
    }

    String captureLevel = null;

//    @BeforeMethod
//    public void setUp(@Optional ITestContext context, @Optional Method method) throws MalformedURLException {
//
//        String applicationType = context.getCurrentXmlTest().getParameter("application.type");
//        captureLevel = context.getSuite().getParameter("capture.level");
//
//        DesiredCapabilities caps = new DesiredCapabilities();
//
//        caps.setCapability("testName", method.getName());
//        caps.setCapability("accessKey", new PropertiesReader().getProperty("ct.accessKey"));
//        caps.setCapability("automationName", "UIAutomator2");
//        caps.setCapability("deviceQuery", "@os='android' and contains(@name, 'Galaxy')");
//
//        if (applicationType.equalsIgnoreCase("unprotected")) {
//
//            caps.setCapability("app", "cloud:uniqueName=StandardApp_CTAppSecIntegration");
//            caps.setCapability("appPackage", "com.experitest.ExperiBank");
//            caps.setCapability("appActivity", ".LoginActivity");
//
//        } else if (applicationType.equalsIgnoreCase("protected")) {
//
//            caps.setCapability("app", "cloud:uniqueName=ProtectedApp_CTDetectedAsThreat");
//            caps.setCapability("appPackage", "com.experitest.ProtectedExperiBank");
//            caps.setCapability("appActivity", ".LoginActivity");
//
//        } else if (applicationType.equalsIgnoreCase("protectedCustomToast")) {
//
//            caps.setCapability("app", "cloud:uniqueName=ProtectedApp_CustomToast");
//            caps.setCapability("appPackage", "com.experitest.ProtectedExperiBank");
//            caps.setCapability("appActivity", ".LoginActivity");
//
//        } else if (applicationType.equalsIgnoreCase("protectedWithoutServer")) {
//
//            caps.setCapability("app", "cloud:uniqueName=ProtectedApp_WithoutServer");
//            caps.setCapability("appPackage", "com.experitest.ProtectedExperiBank");
//            caps.setCapability("appActivity", ".LoginActivity");
//
//        }
//
//        caps.setCapability("fullReset", true);
//        caps.setCapability("attachCrashLogToReport", true);
//
//        desiredCapabilities.set(caps);
//        driver.set(new AndroidDriver(new URL(new PropertiesReader().getProperty("ct.cloudUrl") + "/wd/hub"), caps));
//        wait.set(new WebDriverWait(getDriver(), ofSeconds(10)));
//    }
//
//    @Test
//    public void unprotected_application_test() throws InterruptedException {
//        System.out.println("Unprotected App");
//        ((InteractsWithApps) getDriver()).runAppInBackground(Duration.ofSeconds(-1));
//        startCapturePerformanceMetrics("4G-average", captureLevel, "com.experitest.ExperiBank");
//        ((InteractsWithApps) getDriver()).activateApp("com.experitest.ExperiBank/.LoginActivity");
//        getWait().until(ExpectedConditions.elementToBeClickable(By.id("com.experitest.ExperiBank:id/usernameTextField")));
//        endCapturePerformanceMetrics("login_test-unprotected");
//        Thread.sleep(5000);
//        ((InteractsWithApps) getDriver()).removeApp("com.experitest.ExperiBank");
//    }
//
//    @Test
//    public void protected_application_test_not_detecing_ct() throws InterruptedException {
//        System.out.println("Protected App");
//        ((InteractsWithApps) getDriver()).runAppInBackground(Duration.ofSeconds(-1));
//        startCapturePerformanceMetrics("4G-average", captureLevel, "com.experitest.ProtectedExperiBank");
//        ((InteractsWithApps) getDriver()).activateApp("com.experitest.ProtectedExperiBank/.LoginActivity");
//        getWait().until(ExpectedConditions.elementToBeClickable(By.id("com.experitest.ProtectedExperiBank:id/usernameTextField")));
//        endCapturePerformanceMetrics("launch_app_test-protected");
//        Thread.sleep(5000);
//        ((InteractsWithApps) getDriver()).removeApp("com.experitest.ProtectedExperiBank");
//    }
//
//    @Test
//    public void protected_application_test_custom_toast() throws InterruptedException {
//        System.out.println("Protected App - Custom Toast");
//        getWait().until(ExpectedConditions.elementToBeClickable(By.id("com.experitest.ProtectedExperiBank:id/usernameTextField")));
//        getDriver().findElement(By.id("com.experitest.ProtectedExperiBank:id/usernameTextField")).sendKeys("company");
//
//        getWait().until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@class='android.widget.Toast']")));
//
//        String actualText = getDriver().findElement(By.xpath("//*[@class='android.widget.Toast']")).getAttribute("text");
//        System.out.println("Actual Text: " + actualText);
//        String expectedText = "SCG: Your app has been repackaged. This app has been modified by hackers.";
//        Assert.assertEquals(actualText, expectedText);
//
//        Thread.sleep(10000);
//
//        System.out.println("Protected App - Custom Toast");
//        ((InteractsWithApps) getDriver()).runAppInBackground(Duration.ofSeconds(-1));
//        startCapturePerformanceMetrics("4G-average", captureLevel, "com.experitest.ProtectedExperiBank");
//        ((InteractsWithApps) getDriver()).activateApp("com.experitest.ProtectedExperiBank/.LoginActivity");
//        getWait().until(ExpectedConditions.elementToBeClickable(By.id("com.experitest.ProtectedExperiBank:id/usernameTextField")));
//        endCapturePerformanceMetrics("launch_app_test-protected_custom_toast");
//        Thread.sleep(5000);
//        ((InteractsWithApps) getDriver()).removeApp("com.experitest.ProtectedExperiBank");
//    }
//
//    @Test
//    public void protected_application_test_without_server() throws InterruptedException {
//        System.out.println("Protected App - Without Server");
//
//        ((InteractsWithApps) getDriver()).terminateApp("com.experitest.ProtectedExperiBank");
//        ((InteractsWithApps) getDriver()).runAppInBackground(Duration.ofSeconds(-1));
//
//        startCapturePerformanceMetrics("4G-average", captureLevel, "com.experitest.ProtectedExperiBank");
//
//        ((InteractsWithApps) getDriver()).activateApp("com.experitest.ProtectedExperiBank/.LoginActivity");
//        getWait().until(ExpectedConditions.elementToBeClickable(By.id("com.experitest.ProtectedExperiBank:id/usernameTextField")));
//
//        endCapturePerformanceMetrics("launch_app_test-protected_without_server");
//
//        ((InteractsWithApps) getDriver()).removeApp("com.experitest.ProtectedExperiBank");
//    }
//
//    @AfterMethod(alwaysRun = true)
//    public void tearDown() {
//        if (driver != null) {
//            getDriver().quit();
//            driver.remove();
//            wait.remove();
//        }
//    }

}
