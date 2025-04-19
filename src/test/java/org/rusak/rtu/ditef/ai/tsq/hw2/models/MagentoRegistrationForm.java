package org.rusak.rtu.ditef.ai.tsq.hw2.models;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.rusak.rtu.ditef.ai.tsq.hw2.exceptions.*;
import org.rusak.rtu.ditef.ai.tsq.models.RegistrationFormDOMVO;

import lombok.Getter;
import lombok.Setter;

public class MagentoRegistrationForm {
    @Getter public RegistrationFormDOMVO registrationFormVO;

    @Getter WebDriver driver;
    WebElement firstNameWE;
    WebElement lastNameWE;
    WebElement emailWE;
    WebElement passwordWE;
    WebElement confirmPasswordWE;
    WebElement submitButtonWE;
    WebElement passwordStrengthWE;

    @Getter @Setter public boolean ready;

    public MagentoRegistrationForm(WebDriver driver, RegistrationFormDOMVO registrationFormVO) {
        if (registrationFormVO == null || !registrationFormVO.isReady()) {
            throw new RuntimeException("Registration form is not ready: check url and form elements!");
        }

        this.driver = driver;
        this.registrationFormVO = registrationFormVO;
    }

    public void register() {
        this.findElements();
        if (!this.ready) { throw new RuntimeException("Registration form is not initialized"); }
        this.clear(); //be sure that form is clean
        
        // Fill in the form fields
        // NB fill in password first to trigger its strength meter after email input 
        // TODO some magic happens here -> meter is not always trigerred... <- seems that delay is mandatory
        try {
            Thread.sleep(500); // Slow down input so that strength meter is triggered
            this.passwordWE.sendKeys(registrationFormVO.getPassword().getValue());
            Thread.sleep(500);
            this.emailWE.sendKeys(registrationFormVO.getEmail().getValue());
            Thread.sleep(500);
            //this.emailWE.sendKeys(org.openqa.selenium.Keys.TAB); // simulate focus change to trigger meter
            //Thread.sleep(500);
            this.confirmPasswordWE.sendKeys(registrationFormVO.getPassword().getValue());
            Thread.sleep(500);
            this.firstNameWE.sendKeys(registrationFormVO.getFirstName().getValue());
            Thread.sleep(500);
            this.lastNameWE.sendKeys(registrationFormVO.getLastName().getValue());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread was interrupted during form input slowdown", e);
        }

        // Trigger mouse movement or focus change to ensure the password meter is updated
        /*JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        jsExecutor.executeScript("arguments[0].focus();", this.passwordWE);
        jsExecutor.executeScript("arguments[0].blur();", this.passwordWE);
        jsExecutor.executeScript("arguments[0].focus();", this.emailWE);
        jsExecutor.executeScript("arguments[0].blur();", this.emailWE);*/

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        try {
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.textToBePresentInElement(this.passwordStrengthWE, "Very Strong"),
                    ExpectedConditions.textToBePresentInElement(this.passwordStrengthWE, "Strong")
                )
            );
        } catch (Exception e) {
            throw new WeakPasswordException("Ensure the password meets the required strength criteria.", e);
        }
        
        String passStrength = this.passwordStrengthWE.getText();
        this.registrationFormVO.getPasswordStrengthMeter().setValue(passStrength);
        if (!(passStrength.equals("Strong") || passStrength.equals("Very Strong"))) {
            throw new WeakPasswordException("Password strength is: "+passStrength);
        }

        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(this.submitButtonWE));
        //((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", this.submitButtonWE);
        
        this.submitButtonWE.click();

        // Check that error elements haven't appeared after submit
        try{
            wait = new WebDriverWait(driver, Duration.ofSeconds(1));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(registrationFormVO.getFirstName().getErrorLocator()));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(registrationFormVO.getLastName().getErrorLocator()));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(registrationFormVO.getPassword().getErrorLocator()));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(registrationFormVO.getEmail().getErrorLocator()));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(registrationFormVO.getConfirmPassword().getErrorLocator()));
        } catch (Exception e) {
            throw new MissingRequiredFieldsException("Not all required fields are filled in: "+e.getMessage());
        }

        wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        try {
            WebElement errorMessageElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".message-error.error.message")));
            if (errorMessageElement != null) {
                String errorMessage = errorMessageElement.getText();
                if (errorMessage.contains("There is already an account with this email address")) {
                    throw new DuplicateEmailException("Email is not unique: " + errorMessage);
                }
            }
        } catch (org.openqa.selenium.TimeoutException|org.openqa.selenium.NoSuchElementException e) {
            // No error message found, continue with the registration process
        }
    }

    public void clear() {
        if (this.firstNameWE != null) this.firstNameWE.clear();
        if (this.lastNameWE != null) this.lastNameWE.clear();
        if (this.emailWE != null) this.emailWE.clear();
        if (this.passwordWE != null) this.passwordWE.clear();
        if (this.confirmPasswordWE != null) this.confirmPasswordWE.clear();
    }

    public void findElements() {
        driver.get(registrationFormVO.getUrl());

        try {
            if (registrationFormVO.getFirstName().getLocator() != null) {
                this.firstNameWE = driver.findElement(registrationFormVO.getFirstName().getLocator());
            }
            if (registrationFormVO.getLastName() != null && registrationFormVO.getLastName().getLocator() != null) {
                this.lastNameWE = driver.findElement(registrationFormVO.getLastName().getLocator());
            }
            if (registrationFormVO.getEmail() != null && registrationFormVO.getEmail().getLocator() != null) {
                this.emailWE = driver.findElement(registrationFormVO.getEmail().getLocator());
            }
            if (registrationFormVO.getPassword() != null && registrationFormVO.getPassword().getLocator() != null) {
                this.passwordWE = driver.findElement(registrationFormVO.getPassword().getLocator());
            }
            if (registrationFormVO.getConfirmPassword() != null
                    && registrationFormVO.getConfirmPassword().getLocator() != null) {
                this.confirmPasswordWE = driver.findElement(registrationFormVO.getConfirmPassword().getLocator());
            }
            if (registrationFormVO.getPasswordStrengthMeter() != null
                    && registrationFormVO.getPasswordStrengthMeter().getLocator() != null) {
                this.passwordStrengthWE = driver
                        .findElement(registrationFormVO.getPasswordStrengthMeter().getLocator());
            }
            if (registrationFormVO.getSubmitButton() != null
                    && registrationFormVO.getSubmitButton().getLocator() != null) {
                this.submitButtonWE = driver.findElement(registrationFormVO.getSubmitButton().getLocator());
            }
        } catch (org.openqa.selenium.NoSuchElementException e) {
            throw new RuntimeException("One or more form elements could not be located: " + e.getMessage(), e);
        }
        
        this.ready = true;
    }

    /**
     * - Go to login page
     * - input credentials and submit
     * - be sure that no errors are present
     * - check that user is logged in by going to account page
     */
    public void login() {
        this.findElements();
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(this.registrationFormVO.getEmail().getLocator()));

        // Fill in the form fields
        this.emailWE.sendKeys(registrationFormVO.getEmail().getValue());
        this.passwordWE.sendKeys(registrationFormVO.getPassword().getValue());
        this.submitButtonWE.click();

        // Check that error elements haven't appeared after submit
        wait = new WebDriverWait(driver, Duration.ofSeconds(1));
        try{
            wait.until(ExpectedConditions.invisibilityOfElementLocated(registrationFormVO.getPassword().getErrorLocator()));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(registrationFormVO.getEmail().getErrorLocator()));
        } catch (Exception e) {
            throw new MissingRequiredFieldsException("Not all required fields are filled in: "+e.getMessage());
        }
        
        try {
            WebElement errorMessageElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".message-error.error.message")));
            if (errorMessageElement != null) {
                String errorMessage = errorMessageElement.getText();
                if (errorMessage.contains("Please wait and try again later.")) {
                    throw new InvalidAuthorizationException("Authorization failed: " + errorMessage);
                }
            }
        } catch (org.openqa.selenium.TimeoutException|org.openqa.selenium.NoSuchElementException e) {
            // No error message found, continue with the registration process
        }

        //check that user is at account page
        accountPage(registrationFormVO.getSuccessUrl());
    }

    public void accountPage(String url){
        driver.get(url);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        try{
            wait.until(ExpectedConditions.and(
                    ExpectedConditions.urlMatches(registrationFormVO.getSuccessUrl()),
                    ExpectedConditions.titleIs("My Account")
                )
            );
        } catch (Exception e) {
            throw new NotAuthorizedException("User is not logged in or account page is inaccessible.");
        }

        //check that contact info is correct
        WebElement contactInfoElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".box-information>strong.box-title")));
		if (!contactInfoElement.getText().equals("Contact Information")) {
			throw new NotAuthorizedException("User is not logged in or accessed wrong page. "+ contactInfoElement.getText());
		}
        WebElement contactInfoContentElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".box-information>div.box-content")));
        String actual = contactInfoContentElement.getText();
        String expected = registrationFormVO.getFirstName().getValue()+" "+registrationFormVO.getLastName().getValue()+"\n"+registrationFormVO.getEmail().getValue();
        if (!actual.equals(expected)) {
            throw new NotAuthorizedException("User is not logged in or accessed wrong page."+contactInfoContentElement.getText()+" != "+expected);
        }
    }

    public void logout() {
        // Check that the user is logged in by navigating to the account page
        driver.get(registrationFormVO.getSuccessUrl());

        // Verify that the URL is correct
        if (!driver.getCurrentUrl().equals(registrationFormVO.getSuccessUrl())) {
            throw new NotAuthorizedException("User is not logged in or account page is inaccessible.");
        }

        // Locate the logout link and click it
        WebElement logoutLink = driver.findElements(By.cssSelector(".links>.authorization-link>a")).get(0);
        logoutLink.click();

        // Wait for the logout process to complete and ensure the user is logged out
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.urlContains("logout"));

        // Verify that the user is logged out by checking the presence of the login link
        try {
            WebElement loginLink = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".authorization-link > a")));
            if (!loginLink.getText().equalsIgnoreCase("Sign In")) {
                throw new NotAuthorizedException("Logout failed: Login link not found.");
            }
        } catch (Exception e) {
            throw new RuntimeException("An error occurred during logout verification: " + e.getMessage(), e);
        }
    }
}
