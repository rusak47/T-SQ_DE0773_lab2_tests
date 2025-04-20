package org.rusak.rtu.ditef.ai.tsq.hw2.pages;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import allure.ScreenshooterIntf;
import lombok.Getter;

public class CustomerHomePage implements ScreenshooterIntf{	
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
	
	@FindBy (id = "ui-id-5")
	WebElement mainCategory;
	
	@FindBy (id = "ui-id-17")
	WebElement subCategory;
	
	@FindBy (id = "ui-id-19")
	WebElement productCategory;
	
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
		
		return new HomePage(this.driver);		
	}
	
	public String getWelcomeMessage() {
		
		return this.txtWelcomeMessage.getText();
	}
	
	public void accessCategory(String categoryKeyword) {
		switch(categoryKeyword) {
			case "MenJackets" -> {
							Actions actionbuilder = new Actions(driver);
							WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
							
							wait.until(ExpectedConditions.visibilityOf(this.mainCategory));
							wait.until(ExpectedConditions.elementToBeClickable(this.mainCategory));
							actionbuilder.moveToElement(this.mainCategory).perform();
							
							wait.until(ExpectedConditions.visibilityOf(this.subCategory));
							wait.until(ExpectedConditions.elementToBeClickable(this.subCategory));
							actionbuilder.moveToElement(this.subCategory).perform();
							
							wait.until(ExpectedConditions.visibilityOf(this.productCategory));
							wait.until(ExpectedConditions.elementToBeClickable(this.productCategory));
							actionbuilder.click(this.productCategory).perform();

							wait.until(ExpectedConditions.urlContains("/men/tops-men/jackets-men.html"));
							
						}
			default -> {
                }
		}

		//driver.manage().timeouts().implicitlyWait(Duration.ofMillis(10000));
		takeScreenshot();
	}

}
