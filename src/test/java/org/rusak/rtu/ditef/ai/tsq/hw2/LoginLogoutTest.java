
package org.rusak.rtu.ditef.ai.tsq.hw2;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.rusak.rtu.ditef.ai.tsq.hw2.models.MagentoRegistrationForm;
import org.rusak.rtu.ditef.ai.tsq.models.RegistrationFormDOMVO;
import org.rusak.rtu.ditef.ai.tsq.models.RegistrationFormDOMVO.FormElement;

import allure.Utils;

/**
* Base URL: https://magento.softwaretestingboard.com/
 * 
 * Feature 1: 
 *  In case of success, the account page should containt the user’s first name,
 *  last name in the proper format and e-mail address.
 * @author colt
 */
public class LoginLogoutTest {
	WebDriver driver;

	@BeforeEach
	public void testSetup() throws InterruptedException {
		driver = org.rusak.rtu.ditef.ai.tsq.Utils.setupDriverInstance(driver);
	}
	@AfterEach public void tearDown() {  org.rusak.rtu.ditef.ai.tsq.Utils.quitSharedDriver(driver); }

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

		testLogin(driver, firstName, lastName, email, password);
	}

	public static MagentoRegistrationForm getLoginForm(WebDriver driver, String firstName, String lastName, String email, String password) {
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
				.submitButton(new FormElement(By.cssSelector("button.action.login.primary"), null)) 
				.loginButton(new FormElement(By.cssSelector("ul.header.links li.authorization-link a"), null)) 
				.logoutButton(new FormElement(By.cssSelector("ul.header.links li.authorization-link > a"), null))
				.build()
		);
		return regForm;
	}

	public static MagentoRegistrationForm testLogin(WebDriver driver, String firstName, String lastName, String email, String password) {
		MagentoRegistrationForm regForm = getLoginForm(driver, firstName, lastName, email, password);
		regForm.login();
		return regForm;
	}

	@Test
    public void testLogout() {
		testLogin(driver, "dgblc", "zgcqbq", "gqebhqjn@example.com", "c40C4czRNn");
		MagentoRegistrationForm regForm = new MagentoRegistrationForm(driver, 
			RegistrationFormDOMVO.builder()
				.url("https://magento.softwaretestingboard.com/customer/account/logout/")
				.successUrl("https://magento.softwaretestingboard.com/customer/account/logoutSuccess/")
				.loginButton(new FormElement(By.cssSelector("ul.header.links li.authorization-link a"), null)) 
				.logoutButton(new FormElement(By.cssSelector("ul.header.links li.authorization-link > a"), null))
				.build()
		);
		regForm.findElements(false);
		regForm.logout(null);
	}

}
