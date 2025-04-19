package testRunner.stepDefinitions;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class stepNewCustomerRegTest {
	WebDriver driver;
	final static String FRAMES_URL = "http://demoqa.com/frames-and-windows/";

	@Before
	public void testSetup() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver",	"/usr/bin/chromedriver");
		//WebDriverManager.chromedriver().setup(); //doesnt work
		driver=new 
			//ChromeDriver(); 
			FirefoxDriver();
		driver.manage().window().maximize();
	}

	@After
    public void tearDown() {
        driver.quit();
    }

	@Given("user opens magento shop home page")
	public void user_opens_magento_shop_home_page() {
		driver.get("https://magento.softwaretestingboard.com/");
		//Thread.sleep(100); //it is not recommended for use
		driver.manage().timeouts().implicitlyWait(5000, TimeUnit.MILLISECONDS);

		String title = driver.getTitle();
		System.out.println("Page title is: " + title);
		assertEquals("Home Page", title);
	}

	@When("user register with valid email")
	public void user_register_with_valid_email() {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new io.cucumber.java.PendingException();
	}

	@Then("user is registered succesfully")
	public void user_is_registered_succesfully() {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new io.cucumber.java.PendingException();
	}
	@Then("user logs out")
	public void user_logs_out() {
	    // Write code here that turns the phrase above into concrete actions
	   // throw new io.cucumber.java.PendingException();
	}

	@When("user logs in with {string} {string}")
	public void user_logs_in_with_email_password(String email, String password) {
	    // Write code here that turns the phrase above into concrete actions
	    //throw new io.cucumber.java.PendingException();
	}
}
