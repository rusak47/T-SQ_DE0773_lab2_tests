
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
public class Feature1RegistrationTest {
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
	 * FEATURE 1: Register new unique customer
	 * Description: Users should be able to register using unique emails. To
	 * register all mandatory fields should be filled by a customer. Once the user
	 * has been registered, the user should be redirected to the account page. In
	 * case of success, the account page should containt the user’s first name,
	 * last name in the proper format and e-mail address.
	 * 
	 * • TASK 1-2: Create test or tests
	 * 1. In test if a customer account has not been created, verify error messages when
	 * • Email is not unique,
	 * • Not all mandatory fields are filled in.
	 * 2. In test if a customer account has been created, validate that the customer has
	 * 		been redirected to correct page and that account information is as mentioned
	 * 		in description.
	 * 
	 * successfull:
	 * 	| email | password | firstName | lastName |
	 *  | "gqebhqjn@example.com" | "c40C4czRNn" | "dgblc" | "zgcqbq" |
	 *  | "tbrjpkos@example.com" | "i#$E*I&_v[" | "" | "" |
	 *  | "kyjzeqbz@example.com" | ":]sRsaf3o~" | "" | "" |
	 *  | "uxzxwogx@example.com" | "Xk3A}07YSy" | "mbuqj" | "hvepay" |
	 */
	@Test
    public void testSuccessfulRegistration() {
		testSuccessfulRegistration(driver);
	}

	public static void testSuccessfulRegistration(WebDriver driver) {
		String password = Utils.randomPassword(10,4);
		String email = Utils.randomEmail();
		
		MagentoRegistrationForm regForm = new MagentoRegistrationForm(driver, 
			RegistrationFormDOMVO.builder()
				.url("https://magento.softwaretestingboard.com/customer/account/create/")
				.successUrl("https://magento.softwaretestingboard.com/customer/account/")
				.firstName(new FormElement(By.id("firstname"),By.id("firstname-error"), Utils.randomString(5)))
				.lastName(new FormElement(By.id("lastname"),By.id("lastname-error"), Utils.randomString(6)))
				.email(new FormElement(By.id("email_address"), By.id("email_address-error"), email))
				.password(new FormElement(By.id("password"), By.id("password-error"), password))
				.passwordStrengthMeter(new FormElement(By.cssSelector("span[id='password-strength-meter-label']"), null))
				.confirmPassword(new FormElement(By.id("password-confirmation"), By.id("password-confirmation-error"), password))
				.submitButton(new FormElement(By.cssSelector("button.action.submit.primary"), null)) // Corrected selector
				.build()
		);

		Exception failed = null;
		int counter = 0;
		do {
			counter++;
			try {
				regForm.register();
				failed = null;

			} catch (WeakPasswordException e) {
				System.out.println("password: "+password);
				System.out.println("Registration failed {"+e.getLocalizedMessage()+"}, retrying... " + counter);
				failed = e;
				//re-generate stronger pass
				password = Utils.randomPassword(10, counter > 3 ? 4 : counter);
				regForm.getRegistrationFormVO().getPassword().setValue(password);

			} catch (DuplicateEmailException e) {
				System.out.println("email: "+email);
				System.out.println("Registration failed {"+e.getLocalizedMessage()+"}, retrying... " + counter);
				
				//check Email is not unique, -> regenerate
				failed = e;
				email = Utils.randomEmail();
				regForm.getRegistrationFormVO().getEmail().setValue(email);
				
			} catch (MissingRequiredFieldsException e) {
				throw new AssertionError("Missing  required fields {"+e.getLocalizedMessage()+"}", e);

			} catch (Exception e) {
				throw new AssertionError("Registration failed with unexpected error", e);
			}

		} while (failed != null && counter < 5);

		if (failed != null) {
			throw new AssertionError("Registration failed after 5 attempts", failed);
		}

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.and(
				ExpectedConditions.urlMatches(regForm.getRegistrationFormVO().getSuccessUrl()),
				ExpectedConditions.titleIs("My Account")
			)
		);

		regForm.accountPage(regForm.getRegistrationFormVO().getSuccessUrl());
		
		System.out.println("Registration test passed");
		System.out.println(regForm.getRegistrationFormVO());
	}

	@Test
    public void testDuplicateEmailRegistration(){
		String password = Utils.randomPassword(10,4);
		String email = "gqebhqjn@example.com";
		
		MagentoRegistrationForm regForm = new MagentoRegistrationForm(driver, 
			RegistrationFormDOMVO.builder()
				.url("https://magento.softwaretestingboard.com/customer/account/create/")
				.successUrl("https://magento.softwaretestingboard.com/customer/account/")
				.firstName(new FormElement(By.id("firstname"),By.id("firstname-error"), Utils.randomString(5)))
				.lastName(new FormElement(By.id("lastname"),By.id("lastname-error"), Utils.randomString(6)))
				.email(new FormElement(By.id("email_address"), By.id("email_address-error"), email))
				.password(new FormElement(By.id("password"), By.id("password-error"), password))
				.passwordStrengthMeter(new FormElement(By.cssSelector("span[id='password-strength-meter-label']"), null))
				.confirmPassword(new FormElement(By.id("password-confirmation"), By.id("password-confirmation-error"), password))
				.submitButton(new FormElement(By.cssSelector("button.action.submit.primary"), null)) // Corrected selector
				.build()
		);

		try {
			regForm.register();
		} catch (DuplicateEmailException e) {
			assertTrue(true,"Duplicate email registration test passed");
			return;			
		} catch (Exception e) {
			fail("Unexpected exception: " + e.getMessage());
		}
		fail("Duplicate email registration test failed");		
	}

	@Test
    public void testMissingRequiredFieldsRegistration(){
		String password = Utils.randomPassword(14,4);
		String email = Utils.randomEmail();
		
		MagentoRegistrationForm regForm = new MagentoRegistrationForm(driver, 
			RegistrationFormDOMVO.builder()
				.url("https://magento.softwaretestingboard.com/customer/account/create/")
				.successUrl("https://magento.softwaretestingboard.com/customer/account/")
				.firstName(new FormElement(By.id("firstname"),By.id("firstname-error"), ""))
				.lastName(new FormElement(By.id("lastname"),By.id("lastname-error"), ""))
				.email(new FormElement(By.id("email_address"), By.id("email_address-error"), email))
				.password(new FormElement(By.id("password"), By.id("password-error"), password))
				.passwordStrengthMeter(new FormElement(By.cssSelector("span[id='password-strength-meter-label']"), null))
				.confirmPassword(new FormElement(By.id("password-confirmation"), By.id("password-confirmation-error"), password))
				.submitButton(new FormElement(By.cssSelector("button.action.submit.primary"), null)) // Corrected selector
				.build()
		);

		try {
			regForm.register();
		} catch (MissingRequiredFieldsException e) {
			assertTrue(true,"Missing required fields registration test passed. ");
			return;			
		} catch (Exception e) {
			fail("Unexpected exception: " + e.getMessage());
		}
		fail("Missing required fields registration test failed");
	}
}
