package org.rusak.rtu.ditef.ai.tsq.hw2.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import allure.ScreenshooterIntf;
import lombok.Getter;

public class HomePage implements ScreenshooterIntf {

	public @Getter WebDriver driver;
	public static String URL = "https://magento.softwaretestingboard.com/";
	
	//driver.findElement(By.cssSelector(".page-header a[href*=\"create\"]")).click();
	@FindBy(css = ".page-header a[href*=\"create\"]")
	WebElement linkCreateAccount;
	
	//String homepageurl = "https://magento.softwaretestingboard.com/";
	
	//driver.findElement(By.cssSelector(".page-header a[href*=\"login\"]")).click
	@FindBy(css = ".page-header a[href*=\"login\"]")
	WebElement linkSignin;
	
	private HomePage(WebDriver driver) {
		this.driver = driver;
	}
	
	public static HomePage create(WebDriver driver) {
		HomePage page = new HomePage(driver);
		driver.get(URL);
		PageFactory.initElements(driver, page);
		
		page.takeScreenshot();
		return page;
	}
	
	public RegistrationPage gotoCreateAccount() {
		this.linkCreateAccount.click();
		return 
		//RegistrationPage.create(driver);		
		PageFactory.initElements(this.driver, RegistrationPage.class);		
	}
	
	public SignInPage gotoSignIn() {
		this.linkSignin.click();
		return SignInPage.create(driver);
	}
	
	
}
