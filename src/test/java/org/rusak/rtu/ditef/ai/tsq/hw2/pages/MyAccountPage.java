package org.rusak.rtu.ditef.ai.tsq.hw2.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class MyAccountPage extends CustomerHomePage{
	
	public MyAccountPage(WebDriver driver) {
		super(driver);	
	}

	public static MyAccountPage create(WebDriver driver) {
		MyAccountPage page = new MyAccountPage(driver);
		PageFactory.initElements(driver, page);
		return page;
	}

	public void checkIsOnMyAccountPage() {
		String title = driver.getTitle();
		System.out.println("Page title is: " + title);
	}
}
