package org.rusak.rtu.ditef.ai.tsq;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Utils {

	// FIXME BUG a workaround for doubled window call by @BeforeEach
	private static WebDriver sharedDriver = null;

	public static WebDriver setupDriverInstance(WebDriver driver) {
		// If a driver is passed in, use it
		if(driver != null) { 
			System.out.println("Using provided driver instance. Number of open windows: " + driver.getWindowHandles().size());
			return driver; 
		}
		
		// If we already have a shared driver, reuse it
		if(sharedDriver != null) {
			System.out.println("Reusing existing shared driver. Number of open windows: " + sharedDriver.getWindowHandles().size());
			return sharedDriver;
		}

		// Create a new driver instance
		System.out.println("Creating new Firefox driver instance");
		WebDriverManager.firefoxdriver().setup();
		
		FirefoxOptions options = new FirefoxOptions();
		//options.addArguments("--headless");
		
		sharedDriver = new FirefoxDriver(options);
		System.out.println("Number of open windows: " + sharedDriver.getWindowHandles().size());
		sharedDriver.manage().window().maximize();
		
		return sharedDriver;
	}

	// Add a method to quit the shared driver when tests are complete
	public static void quitSharedDriver(WebDriver driver) {
		if(sharedDriver != null) {
			System.out.println("Quitting shared driver");
			sharedDriver.quit();
			sharedDriver = null;
		}
		if(driver != null) {
			System.out.println("Quitting provided driver");
			driver.quit();
		}
	}

	private static String shuffleString(String input) {
		List<Character> characters = new ArrayList<>();
		for (char c : input.toCharArray()) {
			characters.add(c);
		}
		Collections.shuffle(characters);
		StringBuilder result = new StringBuilder();
		for (char c : characters) {
			result.append(c);
		}
		return result.toString();
	}
    @SuppressWarnings("deprecation")
	static public String randomString(int length){
		return RandomStringUtils.random(length, 'a', 'z' + 1, true, false);
	}

	static public String randomEmail(){ return randomEmail(8, "example.com"); }
	@SuppressWarnings("deprecation")
	static public String randomEmail(int length, String domain){
		return RandomStringUtils.random(length, 'a', 'z' + 1, true, false)+"@"+domain;
	}

	static public String randomPassword(int length){ return randomPassword(length, 0); }
	@SuppressWarnings("deprecation")
	static public String randomPassword(int length, int mode){
		// small letters only
		if (mode == 1) {
			String lower = RandomStringUtils.random(length, "abcdefghijklmnopqrstuvwxyz");
			return shuffleString(lower);
		}
		// small and big letters
		if (mode == 2) {
			String lower = RandomStringUtils.random(1, "abcdefghijklmnopqrstuvwxyz");
			
			String upper = RandomStringUtils.random(1, "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
			
			String remaining = RandomStringUtils.random(length - 2, "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
			return shuffleString(lower + upper + remaining);
		}
		// small, big letters and numbers only
		if (mode == 3) {
			
			String lower = RandomStringUtils.random(1, "abcdefghijklmnopqrstuvwxyz");
			
			String upper = RandomStringUtils.random(1, "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
			
			String digit = RandomStringUtils.random(1, "0123456789");
			
			String remaining = RandomStringUtils.random(length - 3, "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");
			return shuffleString(lower + upper + digit + remaining);
		}
		// small, big letters, numbers and special symbols only
		if (mode == 4) {
			
			String lower = RandomStringUtils.random(1, "abcdefghijklmnopqrstuvwxyz");
			
			String upper = RandomStringUtils.random(1, "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
			
			String digit = RandomStringUtils.random(1, "0123456789");
			
			String special = RandomStringUtils.random(1, "!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~");
			
			String remaining = RandomStringUtils.random(length - 4, "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~");
			return shuffleString(lower + upper + digit + special + remaining);
		}
		return RandomStringUtils.random(length, "abcdefghijklmnopqrstuvwxyz");
	}
}
