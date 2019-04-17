package com.saucelabs.demos;

import com.saucelabs.common.SauceOnDemandAuthentication;
import com.saucelabs.common.SauceOnDemandSessionIdProvider;
import com.saucelabs.junit.SauceOnDemandTestWatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.HashMap;

public class PreRunTest implements SauceOnDemandSessionIdProvider {



    /**
     * Constructs a {@link SauceOnDemandAuthentication} instance using the supplied user name/access key.  To use the authentication
     * supplied by environment variables or from an external file, use the no-arg {@link SauceOnDemandAuthentication} constructor.
     */
    public SauceOnDemandAuthentication authentication = new SauceOnDemandAuthentication(System.getenv("SAUCECON19_USERNAME"),
            System.getenv("SAUCECON19_ACCESS_KEY"));

    /**
     * JUnit Rule which will mark the Sauce Job as passed/failed when the test succeeds or fails.
     */
    @Rule
    public SauceOnDemandTestWatcher resultReportingTestWatcher = new SauceOnDemandTestWatcher(this, authentication);

    /**
     * Instance variable which contains the Sauce Job Id.
     */
    private String sessionId;

    /**
     * The {@link WebDriver} instance which is used to perform browser interactions with.
     */
    private WebDriver driver;

    private SimpleDateFormat datetime = new SimpleDateFormat("yyyyMMddHHmm");

    public PreRunTest() {
        super();
    }

    @Before
    public void setUp() throws Exception {


        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platform", "Windows 10");
        caps.setCapability("browserName", "MicrosoftEdge");
        caps.setCapability("version", "17");
        //caps.setCapability("extendedDebugging", true);
        caps.setCapability("name", "Pre Run Demo Test");
        caps.setCapability("build", "SauceCon 19 Troubleshooting Demo Tests, " + datetime.format(System.currentTimeMillis()));

        HashMap<String,Object> prerun = new HashMap<String,Object>();
        prerun.put("executable","https://raw.githubusercontent.com/mattdsauce/saucecon19-troubleshooting/master/editreg.cmd");
        prerun.put("background",false);
        //prerun.put("timeout","120");

        caps.setCapability("prerun", prerun);

        driver = new RemoteWebDriver(
                new URL("https://" + authentication.getUsername() + ":" + authentication.getAccessKey() + "@ondemand.saucelabs.com/wd/hub"),
                caps);
        sessionId = (((RemoteWebDriver) driver).getSessionId()).toString();
    }

    /**
     * Runs a simple test verifying the title of the amazon.com homepage.
     * @throws Exception
     */
    @Test
    public void doTest() throws Exception {
        //driver.get("https://httpbin.org/basic-auth/Username/password");
        driver.get("https://www.onlinemictest.com/");
        Thread.sleep(5000);

        WebElement el = driver.findElement(By.id("audio-start"));
        el.click();

        Thread.sleep(15000);

    }

    /**
     * Closes the {@link WebDriver} session.
     *
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
        driver.quit();
    }

    /**
     *
     * @return the value of the Sauce Job id.
     */
    @Override
    public String getSessionId() {
        return sessionId;
    }
}
