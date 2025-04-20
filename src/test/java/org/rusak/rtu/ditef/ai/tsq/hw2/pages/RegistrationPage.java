package org.rusak.rtu.ditef.ai.tsq.hw2.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RegistrationPage {
		
	/*
	    driver.findElement(By.id("firstname")).sendKeys(firstName);
		driver.findElement(By.id("lastname")).sendKeys(lastName);
		driver.findElement(By.id("email_address")).sendKeys(email);
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.id("password-confirmation")).sendKeys(password);
		
		driver.findElement(By.cssSelector("#form-validate.create")).submit();
	 */
	
	@FindBy(id = "firstname")
	WebElement firstname;
	
	@FindBy(id = "lastname")
	WebElement lastname;
	
	@FindBy(id = "email_address")
	WebElement email_address;
	
	@FindBy(id = "password")
	WebElement password;
	
	@FindBy(id = "password-confirmation")
	WebElement passwordConfirmation;
	
	@FindBy(css = "#form-validate.create")
	WebElement formSubmit;
	
	
WebDriver driver;
	
	public RegistrationPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public MyAccountPage createAnAccount(String firstName, String lastName, String email, String password) {
		
		this.firstname.sendKeys(firstName);
		this.lastname.sendKeys(lastName);
		this.email_address.sendKeys(email);
		this.password.sendKeys(password);
		this.passwordConfirmation.sendKeys(password);
		
		this.formSubmit.submit();
		
		return PageFactory.initElements(this.driver, MyAccountPage.class);
		
	}

}
