package org.rusak.rtu.ditef.ai.tsq.hw2.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignInPage {
	
	@FindBy (id = "email")
	WebElement el_email;
	
	@FindBy (css = "input[name=\"login[password]\"" )
	WebElement el_password;
	
	@FindBy (css = "#send2.action.login.primary")
	WebElement el_submit; 
	
	WebDriver driver;
	
	public SignInPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public CustomerHomePage signIn(String email, String password) {
		//this.el_email.sendKeys(email);
		//this.el_password.sendKeys(password);
		//this.el_submit.click();
		
		Actions actionbuilder = new Actions(this.driver);
		actionbuilder
			.sendKeys(el_email, email)
			.sendKeys(el_password, password)
			.click(el_submit)
			.build().perform();	
		
		return PageFactory.initElements(this.driver, CustomerHomePage.class);
	}

}
