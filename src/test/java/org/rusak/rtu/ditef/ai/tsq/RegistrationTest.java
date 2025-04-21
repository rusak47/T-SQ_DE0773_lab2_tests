
package org.rusak.rtu.ditef.ai.tsq;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.PageFactory;
import org.rusak.rtu.ditef.ai.tsq.models.BookStoreRegistrationForm;
import org.rusak.rtu.ditef.ai.tsq.models.RegistrationFormVO;

/**
 *
 * @author colt
 */
@Disabled
public class RegistrationTest {
	WebDriver driver;

	@BeforeEach
	public void testSetup() throws InterruptedException {
		driver = Utils.setupDriverInstance(driver);
	}
	@AfterEach public void tearDown() {Utils.quitSharedDriver(driver);}

    @Test
    public void testRegistration(){
		BookStoreRegistrationForm regForm = PageFactory.initElements(driver, BookStoreRegistrationForm.class);
		
		 // Assert that all elements are initialized
		 assertTrue(regForm.locatedFirstName(), "First name element was not found!");
		 assertTrue(regForm.locatedLastName(), "Last name element was not found!");
		 assertTrue(regForm.locatedUsername(), "Username element was not found!");
		 assertTrue(regForm.locatedPassword(), "Password element was not found!");
		 assertTrue(regForm.locatedRecaptcha(), "Recaptcha element was not found!");
		 assertTrue(regForm.locatedRegister(), "Register button element was not found!");
	 
		regForm.init(driver, 
			RegistrationFormVO.builder()
				.firstName(Utils.randomString(5))
				.lastName(Utils.randomString(6))
				.username(Utils.randomString(8))
				.password(Utils.randomString(10))
				.build()
		);
		
		regForm.register();

		System.out.println("Registration test passed");
		System.out.println("Registration form: " + regForm.getRegistrationFormVO());
    }
}