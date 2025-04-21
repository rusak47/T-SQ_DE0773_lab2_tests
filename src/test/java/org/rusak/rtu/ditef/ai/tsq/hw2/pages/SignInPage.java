package org.rusak.rtu.ditef.ai.tsq.hw2.pages;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.rusak.rtu.ditef.ai.tsq.hw2.models.DelayIntf;

import allure.ScreenshooterIntf;
import lombok.Getter;

public class SignInPage implements ScreenshooterIntf, DelayIntf{
	
	@FindBy (id = "email")
	WebElement el_email;
	
	@FindBy (css = "input[name=\"login[password]\"" )
	WebElement el_password;
	
	@FindBy (css = "#send2.action.login.primary")
	WebElement el_submit; 
	
	public @Getter WebDriver driver;
	
	private SignInPage(WebDriver driver) {
		this.driver = driver;
	}

	public static SignInPage create(WebDriver driver) {
		SignInPage page = new SignInPage(driver);
		PageFactory.initElements(driver, page);

		page.waittoappear(1000);
		page.takeScreenshot();
		return page;
	}
	
	public MyAccountPage signIn(String email, String password) {
		//this.el_email.sendKeys(email);
		//this.el_password.sendKeys(password);
		//this.el_submit.click();
		
		Actions actionbuilder = new Actions(this.driver);
		actionbuilder
			.sendKeys(el_email, email)
			.sendKeys(el_password, password)
			.click(el_submit)
			.build().perform();	

		this.waittoappear(5000);
		this.takeScreenshot();
		return MyAccountPage.create(driver);		
	}

	public void checkIsOnSignInPage() {
		String title = driver.getTitle();
		System.out.println("Page title is: " + title);
		assertEquals("Customer Login", title);
		
		assertTrue(this.el_email.isDisplayed());
		assertTrue(this.el_password.isDisplayed());
		assertTrue(this.el_submit.isDisplayed());
	}

}
