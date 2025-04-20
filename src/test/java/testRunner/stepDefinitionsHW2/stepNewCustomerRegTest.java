package testRunner.stepDefinitionsHW2;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.rusak.rtu.ditef.ai.tsq.Utils;
import org.rusak.rtu.ditef.ai.tsq.hw2.LoginLogoutTest;
import org.rusak.rtu.ditef.ai.tsq.hw2.models.MagentoRegistrationForm;
import org.rusak.rtu.ditef.ai.tsq.hw2.pages.CustomerHomePage;
import org.rusak.rtu.ditef.ai.tsq.hw2.pages.HomePage;
import org.rusak.rtu.ditef.ai.tsq.hw2.pages.MyAccountPage;
import org.rusak.rtu.ditef.ai.tsq.hw2.pages.RegistrationPage;
import org.rusak.rtu.ditef.ai.tsq.hw2.pages.SignInPage;
import org.rusak.rtu.ditef.ai.tsq.models.RegistrationFormDOMVO;
import org.rusak.rtu.ditef.ai.tsq.models.RegistrationFormDOMVO.FormElement;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class stepNewCustomerRegTest {
	WebDriver driver;
	MagentoRegistrationForm form;
	
	HomePage homepage;
	RegistrationPage regpage;
	MyAccountPage accountpage;
	SignInPage signinpage;
	CustomerHomePage customerhomepage;

	@Before
	public void testSetup() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver",	"/usr/bin/chromedriver");
		//WebDriverManager.chromedriver().setup(); //doesnt work
		driver=new 
			//ChromeDriver(); 
			FirefoxDriver();
		driver.manage().window().maximize();
	}

	@After public void tearDown() { driver.quit(); }

	@Given("user opens magento shop home page and goes to registration page")
	public void user_opens_magento_shop_home_page_and_navigates_to_registration() {
		driver.get("https://magento.softwaretestingboard.com/");
		driver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);

		String title = driver.getTitle();
		System.out.println("Page title is: " + title);
		assertEquals("Home Page", title);

		String password = Utils.randomPassword(10,4);
		String email = Utils.randomEmail();
		
		WebElement loginLink = driver.findElements(By.cssSelector("ul.header.links li.authorization-link+li a")).get(0);
        String registrationUrl = loginLink.getDomAttribute("href");
		loginLink.click();

        // Wait for redirect process to complete and ensure the user is at registration page
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.urlContains("/customer/account/create/"));

		form = new MagentoRegistrationForm(driver, 
			RegistrationFormDOMVO.builder()
				.url(registrationUrl)
				.successUrl("https://magento.softwaretestingboard.com/customer/account/")
				.firstName(new FormElement(By.id("firstname"),By.id("firstname-error"), Utils.randomString(5)))
				.lastName(new FormElement(By.id("lastname"),By.id("lastname-error"), Utils.randomString(6)))
				.email(new FormElement(By.id("email_address"), By.id("email_address-error"), email))
				.password(new FormElement(By.id("password"), By.id("password-error"), password))
				.passwordStrengthMeter(new FormElement(By.cssSelector("span[id='password-strength-meter-label']"), null))
				.confirmPassword(new FormElement(By.id("password-confirmation"), By.id("password-confirmation-error"), password))
				.submitButton(new FormElement(By.cssSelector("button.action.submit.primary"), null)) 
				.loginButton(new FormElement(By.cssSelector("ul.header.links li.authorization-link a"), null)) 
				.logoutButton(new FormElement(By.cssSelector("ul.header.links li.authorization-link > a"), null))
				.build()
		);
		form.findElements(false); //redirect to registration page and init form elements
	}

	@When("user register with valid email")
	public void user_register_with_valid_email() {
	    form.fillFormFields();
		form.validatePasswordStrength();
		form.submitForm();
	}

	@Then("user is registered succesfully")
	public void user_is_registered_succesfully() {
	    form.verifyRegistrationSuccess();
	}

	@Then("user logs out")
	public void user_logs_out() {
		form.logout(null);
	}


	@Given("user opens magento shop home page")
	public void user_opens_magento_shop_home_page() {
		driver.get("https://magento.softwaretestingboard.com/");
		driver.manage().timeouts().implicitlyWait(1000, TimeUnit.MILLISECONDS);

		String title = driver.getTitle();
		System.out.println("Page title is: " + title);
		assertEquals("Home Page", title);

		WebElement loginLink = driver.findElements(By.cssSelector("ul.header.links li.authorization-link a")).get(0);
        String loginUrl = loginLink.getDomAttribute("href");
		loginLink.click();

        // Wait for redirect process to complete and ensure the user is at registration page
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.urlContains("/customer/account/login/referer/"));

		form = LoginLogoutTest.getLoginForm(driver, "", "", "", "");
		form.findElements(false); 
	}

	//@When("^User logs in with (.*) (.*)$")
	@When("user {string} {string} logs in with {string} {string}")
	public void user_logs_in_with_email_password(String firstname, String lastname, String email, String password) {
	    form.getRegistrationFormVO().getEmail().setValue(email);
		form.getRegistrationFormVO().getPassword().setValue(password);
		form.getRegistrationFormVO().getFirstName().setValue(firstname);
		form.getRegistrationFormVO().getLastName().setValue(lastname);
		
		form.login();
	}

	@And("user opens catalog of mens jackets")
	public void user_opens_catalog_of_mens_jackets() {
		this.customerhomepage = CustomerHomePage.create(driver);
		
		//focus on .navigation ul#ui-id-2>li+li+li a
		//driver.findElement(By.cssSelector(".navigation ul#ui-id-2>li+li+li a"));
		this.customerhomepage.accessCategory("MenJackets");
	}

	@Then("user is on catalogue of men jackets page")
	public void user_is_on_catalogue_of_men_jackets_page() {
		String url = driver.getCurrentUrl();
		assertEquals("https://magento.softwaretestingboard.com/men/tops-men/jackets-men.html", url);
	}
}
