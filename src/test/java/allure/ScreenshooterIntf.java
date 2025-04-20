package allure;

import org.openqa.selenium.WebDriver;

/**
 * Interface for taking screenshots
 *  with default method that calls Utils.takeScreenshot()
 * @author colt
 */
public interface ScreenshooterIntf {
    public default byte[] takeScreenshot() { 
        System.out.println("ScreenshooterIntf: Taking screenshot");
        return Utils.takeScreenshot(getDriver());
     }

    WebDriver getDriver();
}
