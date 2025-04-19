package org.rusak.rtu.ditef.ai.tsq;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

@Disabled
class ProofOfConceptTest {

	WebDriver driver;
	final static String FRAMES_URL = "http://demoqa.com/frames-and-windows/";

	@BeforeEach
	public void testSetup() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver",	"/usr/bin/chromedriver");
		//WebDriverManager.chromedriver().setup(); //doesnt work
		driver=new 
			//ChromeDriver(); 
			FirefoxDriver();
		driver.manage().window().maximize();
	}

	@AfterEach
    public void tearDown() {
        driver.quit();
    }

	@Test
	void test() {
		driver.get("https://demoqa.com/elements");
		//Thread.sleep(100); //it is not recommended for use
		driver.manage().timeouts().implicitlyWait(5000, TimeUnit.MILLISECONDS);

		String title = driver.getTitle();
		System.out.println("Page title is: " + title);

	}

	@Test
	void testClicks() {
		driver.get("https://demoqa.com/buttons");
        driver.findElement(By.xpath("doubleClickBtn")).click();
//        String result = driver.findElementById("button1_status").getText();
//        System.out.println("Button 1 status: " + result);
//
//        driver.findElementById("button2").click();
//        result = driver.findElementById("button2_status").getText();
//        System.out.println("Button 2 status: " + result);
//
//        driver.findElementById("button3").click();
//        try {
//            result = driver.findElementById("button3_status").getText();
//            System.out.println("Button 3 status: " + result);
//        } catch (Exception e) {
//            fail("Button 3 not found");
//        }
	}

	@Test
	void testFrames() {
		driver.get(FRAMES_URL);
        driver.switchTo().frame(0); // switch to the first frame
        String title = driver.getTitle();
        System.out.println("Page title in frame 1 is: " + title);
        driver.switchTo().defaultContent(); // switch back to the main page

        driver.switchTo().frame(1); // switch to the second frame
        title = driver.getTitle();
        System.out.println("Page title in frame 2 is: " + title);
        driver.switchTo().defaultContent(); // switch back to the main page

        driver.switchTo().frame(2); // switch to the third frame
	}

}
