package org.rusak.rtu.ditef.ai.tsq;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class UtilsTest {

	@Test
	public void testRandomStringLength() {
		int length = 10;
		String randomStr = Utils.randomString(length);
		assertNotNull(randomStr, "Generated string should not be null");
		assertEquals(length, randomStr.length(), "Generated string should have the specified length");
	}

	@Test
	public void testRandomStringContent() {
		int length = 15;
		String randomStr = Utils.randomString(length);
		assertTrue(randomStr.chars().allMatch(ch -> ch >= 'a' && ch <= 'z'),
				"Generated string should only contain lowercase alphabetic characters");
	}

	@Test
	public void testRandomStringUniqueness() {
		String randomStr1 = Utils.randomString(8);
		String randomStr2 = Utils.randomString(8);
		assertNotEquals(randomStr1, randomStr2, "Generated strings should be unique");
	}

	@Test
	public void testRandomEmail() {
		String randomEmail = Utils.randomEmail();
		assertTrue(randomEmail.matches("[a-z]+@example\\.com"), "Generated email should match the expected format");
	}

	@Test
	public void testRandomEmailWithCustomDomain() {
		String customDomain = "test.com";
		String randomEmail = Utils.randomEmail(10, customDomain);
		assertTrue(randomEmail.matches("[a-z]+@" + customDomain), "Generated email should match the expected format");
	}

	@Test
	public void testRandomPassword() {
		String randomPassword = Utils.randomPassword(12, 1);
		assertTrue(randomPassword.matches("[a-z]+"), "Generated password should only contain lowercase alphabetic characters");
	}
	@Test
	public void testRandomPasswordWithMixedCase() {
		String randomPassword = Utils.randomPassword(12, 2);
		assertTrue(randomPassword.matches("[a-zA-Z]+"),
				"Generated password should contain both lowercase and uppercase alphabetic characters");
	}

	@Test
	public void testRandomPasswordWithNumbers() {
		String randomPassword = Utils.randomPassword(12, 3);
		assertTrue(randomPassword.matches("[a-zA-Z0-9]+"),
				"Generated password should contain lowercase, uppercase alphabetic characters, and numbers");
	}

	@Test
	public void testRandomPasswordWithSpecialCharacters() {
		String randomPassword = Utils.randomPassword(12, 4);
		// Ensure the password contains at least one alphabetic character
		assertTrue(randomPassword.matches(".*[a-zA-Z].*"), 
		"Generated password should contain at least one alphabetic character");

		// Ensure the password contains at least one numeric character
		assertTrue(randomPassword.matches(".*[0-9].*"), 
				"Generated password should contain at least one numeric character");

		// Ensure the password contains at least one special character
		assertTrue(randomPassword.matches(".*[\\x21-\\x2F\\x3A-\\x40\\x5B-\\x60\\x7B-\\x7E].*"), 
				"Generated password should contain at least one special character");

		// Ensure the password only contains valid characters
		assertTrue(randomPassword.matches("[\\x21-\\x7E]+"), 
				"Generated password should only contain valid printable ASCII characters");

		System.out.println(randomPassword);
	}

	@Test
	public void testRandomPasswordLength() {
		int length = 16;
		String randomPassword = Utils.randomPassword(length, 4);
		assertNotNull(randomPassword, "Generated password should not be null");
		assertEquals(length, randomPassword.length(), "Generated password should have the specified length");
	}

	@Test
	public void testRandomPasswordUniqueness() {
		String randomPassword1 = Utils.randomPassword(10, 3);
		String randomPassword2 = Utils.randomPassword(10, 3);
		assertNotEquals(randomPassword1, randomPassword2, "Generated passwords should be unique");
	}
}
