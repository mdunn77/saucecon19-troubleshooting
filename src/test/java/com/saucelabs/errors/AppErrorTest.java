package com.saucelabs.errors;

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.junit.SauceOnDemandTestWatcher;
import io.appium.java_client.AppiumDriver;
import org.junit.*;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;
import java.net.URL;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;

public class AppErrorTest implements SauceOnDemandSessionIdProvider {

    /**
     * Test to reproduce "Infrastructure Error -- The Sauce VMs failed to start the browser or device" due to bad app URL
     */

    private AppiumDriver<WebElement> driver;

    private String sessionId;

    private SimpleDateFormat datetime = new SimpleDateFormat("yyyyMMddHHmm");

    /**
     * Constructs a {@link SauceOnDemandAuthentication} instance using the supplied user name/access key.  To use the authentication
     * supplied by environment variables or from an external file, use the no-arg {@link SauceOnDemandAuthentication} constructor.
     */
    public SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication(System.getenv("SAUCECON19_USERNAME"), System.getenv("SAUCECON19_ACCESS_KEY"));

    /**
     * JUnit Rule which will mark the Sauce Job as passed/failed when the test succeeds or fails.
     */
    public @Rule
    SauceOnDemandTestWatcher resultReportingTestWatcher = new SauceOnDemandTestWatcher(this, authentication);

    /**
     * Sets up appium.  You will need to either explictly set the sauce username/access key variables, or set
     * SAUCE_USERNAME and SAUCE_ACCESS_KEY environment variables to reference your Sauce account details.
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("platformVersion", "8.0");
        capabilities.setCapability("deviceName", "Android GoogleAPI Emulator");
        capabilities.setCapability("appiumVersion", "1.9.1");
        capabilities.setCapability("name", "App Error Test");
        capabilities.setCapability("build", "SauceCon 19 Troubleshooting, " + datetime.format(System.currentTimeMillis()));
        capabilities.setCapability("app", "https://github.com/mattdsauce/sauce-project/blob/master/Calculator_2.0.apk");
        //capabilities.setCapability("app", "https://github.com/mattdsauce/sauce-project/blob/master/Calculator_2.0.apk?raw=true");

        driver = new AppiumDriver<WebElement>(new URL(MessageFormat.format("https://{0}:{1}@ondemand.saucelabs.com/wd/hub", authentication.getUsername(), authentication.getAccessKey())),
                capabilities);
        this.sessionId = driver.getSessionId().toString();
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
    }


    @Test
    public void doTest() throws InterruptedException, IOException {

        driver.getPageSource();

        Thread.sleep(2000);
    }

    public String getSessionId() {
        return sessionId;
    }
}