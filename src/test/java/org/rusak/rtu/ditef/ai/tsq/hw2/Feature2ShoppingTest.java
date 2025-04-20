
package org.rusak.rtu.ditef.ai.tsq.hw2;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
* Base URL: https://magento.softwaretestingboard.com/
 * 
  * Feature 2: Log in a customer and verify products in the cart as follows:
 *  Task 1: Sort products in another category by price in descending order.
 *         Verify that the first product has the maximal price in the category.
 * 
 *  Task 2: Sort products in a category by price in ascending order.
 *         Verify that the first product has the minimal price in the category.
 *         Search a product by its full or partial title.
 *         Verify that the product found has the text that was searched.
 * 
 *  Task 3: Add to the cart a product of the third category with the maximum
 *         price and the minimal price.
 *         Add to the cart a product found via the search bar.
 *         Verify that the cart has been updated correctly when each product
 *         is added from the product page by product title, quantity, and price.
 * @author colt
 */
public class Feature2ShoppingTest {
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


}
