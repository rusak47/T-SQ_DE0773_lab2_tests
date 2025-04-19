
package org.rusak.rtu.ditef.ai.tsq.hw2;

import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.rusak.rtu.ditef.ai.tsq.Utils;
import org.rusak.rtu.ditef.ai.tsq.hw2.models.MagentoRegistrationForm;
import org.rusak.rtu.ditef.ai.tsq.models.RegistrationFormDOMVO;
import org.rusak.rtu.ditef.ai.tsq.models.RegistrationFormDOMVO.FormElement;
import org.rusak.rtu.ditef.ai.tsq.hw2.exceptions.*;

/**
* Base URL: https://magento.softwaretestingboard.com/
 * 
 * Feature 1: Register new unique customer
 *  Task 1: Verify that the customer is registered successfully, if a customer
 *         with the same e-mail does not exist.
 * 
 *  Task 2: Verify the error message if a customer with the same e-mail already
 *         exists.
 * @author colt
 */
public class LoginLogoutTest {
	WebDriver driver;

	@BeforeEach
	public void testSetup() throws InterruptedException {
		System.setProperty("webdriver.chrome.driver",	"/usr/bin/chromedriver");
		//WebDriverManager.chromedriver().setup(); //doesnt work
		driver=new 
			//ChromeDriver(); 
			FirefoxDriver();
		driver.manage().window().maximize();
	}

	@AfterEach public void tearDown() {  driver.quit(); }

	/**
	 * 	| email | password | firstName | lastName |
	 *  | "gqebhqjn@example.com" | "c40C4czRNn" | "dgblc" | "zgcqbq"
	 */
	@Test
    public void testLogin() {
		String email = "gqebhqjn@example.com";
		String password = "c40C4czRNn";
		String firstName = "dgblc";
		String lastName = "zgcqbq";
		
		MagentoRegistrationForm regForm = new MagentoRegistrationForm(driver, 
			RegistrationFormDOMVO.builder()
				.url("https://magento.softwaretestingboard.com/customer/account/login/")
				.successUrl("https://magento.softwaretestingboard.com/customer/account/")
				.email(new FormElement(
					By.cssSelector(".fieldset.login .field.email input#email"),
					By.id("email-error"), 
					 email))
				.password(new FormElement(
					By.cssSelector("form[id=\"login-form\"]:first-child > .fieldset.login > div.field.password input[id=\"pass\"]"), 
					By.id("pass-error"), 
					password))
				.firstName(new FormElement(null,null, firstName, false))
				.lastName(new FormElement(null,null, lastName, false))	
				.submitButton(new FormElement(By.cssSelector("button.action.login.primary"), null)) // Corrected selector
				.build()
		);

		regForm.login();
	}

	@Test
    public void testLogout() {
		String email = "gqebhqjn@example.com";
		String password = "c40C4czRNn";
		
		MagentoRegistrationForm regForm = new MagentoRegistrationForm(driver, 
			RegistrationFormDOMVO.builder()
				.url("https://magento.softwaretestingboard.com/customer/account/login/")
				.successUrl("https://magento.softwaretestingboard.com/customer/account/")
				.email(new FormElement(By.id("email_address"), By.id("email_address-error"), email))
				.password(new FormElement(By.id("password"), By.id("password-error"), password))
				.submitButton(new FormElement(By.cssSelector("button.action.submit.primary"), null)) // Corrected selector
				.build()
		);

		regForm.login();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.and(
				ExpectedConditions.urlMatches(regForm.getRegistrationFormVO().getSuccessUrl()),
				ExpectedConditions.titleIs("My Account")
			)
		);

		//String accountPageHeader = driver.findElement(By.cssSelector(".page-title")).getText();
		//assertTrue(accountPageHeader.contains("My Account"), "User was not redirected} to the account page.");
	}

}
