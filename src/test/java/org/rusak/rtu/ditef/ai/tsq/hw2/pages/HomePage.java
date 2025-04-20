package org.rusak.rtu.ditef.ai.tsq.hw2.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

	WebDriver driver;
	public static String URL = "https://magento.softwaretestingboard.com/";
	
	//driver.findElement(By.cssSelector(".page-header a[href*=\"create\"]")).click();
	@FindBy(css = ".page-header a[href*=\"create\"]")
	WebElement linkCreateAccount;
	
	//String homepageurl = "https://magento.softwaretestingboard.com/";
	
	//driver.findElement(By.cssSelector(".page-header a[href*=\"login\"]")).click
	@FindBy(css = ".page-header a[href*=\"login\"]")
	WebElement linkSignin;
	
	public HomePage(WebDriver driver) {
		this.driver = driver;
		driver.get(URL);
		PageFactory.initElements(this.driver, this);
	}
	
	public RegistrationPage createAnAccount() {
		this.linkCreateAccount.click();
		return PageFactory.initElements(this.driver, RegistrationPage.class);
		
	}
	
	public SignInPage signIn() {
		this.linkSignin.click();
		return PageFactory.initElements(this.driver, SignInPage.class);
	}
	
	
}
