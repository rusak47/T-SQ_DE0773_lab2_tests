package org.rusak.rtu.ditef.ai.tsq.hw2.pages;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.rusak.rtu.ditef.ai.tsq.hw2.models.DelayIntf;
import org.rusak.rtu.ditef.ai.tsq.hw2.models.TopNavigationIntf;

import allure.ScreenshooterIntf;
import lombok.Getter;

public class CustomerHomePage implements TopNavigationIntf, ScreenshooterIntf, DelayIntf{	
	public @Getter WebDriver driver;
	
	//driver.findElement(By.cssSelector(".panel.header  .action.switch")).click();
	//driver.findElement(By.cssSelector(".panel.header  a[href*=\"logout\"]")).click();	
	
	//NB use of annotations doesnt allow to init objects from constructor
	@FindBy(css = ".panel.header  .action.switch")
	WebElement dropDownMenu;
	
	@FindBy(css = ".panel.header  a[href*=\"logout\"]")
	WebElement linkSignOut;
	
	@FindBy (css = ".panel.header .logged-in")
	WebElement txtWelcomeMessage;
	
	protected CustomerHomePage(WebDriver driver) {
		this.driver = driver;
	}

	public static CustomerHomePage create(WebDriver driver) {
		CustomerHomePage page = new CustomerHomePage(driver);
		PageFactory.initElements(driver, page);
		return page;
	}
	
	public HomePage signOut() {
		this.dropDownMenu.click();
		this.linkSignOut.click();
		
		return HomePage.create(this.driver);		
	}
	
	public String getWelcomeMessage() {		
		return this.txtWelcomeMessage.getText();
	}
	
	public void checkIsOnCustomerHomePage() {
		String title = driver.getTitle();
		System.out.println("Page title is: " + title);
		assertEquals("Home Page", title);

		assertTrue(this.txtWelcomeMessage.isDisplayed());
	}

	@Override public void clearCart(){
        TopNavigationIntf.super.clearCart();
		waittoappear(1000);
        takeScreenshot();
    }

	@Override public ProductsPage accessCategory(String root, String category, String subcategory) {
		ProductsPage products = (ProductsPage) TopNavigationIntf.super.accessCategory(root, category, subcategory);
		waittoappear(150);
		return products;
	}
}
