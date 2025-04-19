package org.rusak.rtu.ditef.ai.tsq.models;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class BookStoreRegistrationForm {
    @Getter public RegistrationFormVO registrationFormVO;

    @Getter WebDriver driver;
    @FindBy(id = "firstname")
    WebElement firstNameWE;
    @FindBy(id = "lastname")
    WebElement lastNameWE;
    @FindBy(id = "userName")
    WebElement usernameWE;
    @FindBy(id = "password")
    WebElement passwordWE;
    @FindBy(
        //id = "recaptcha-anchor"
        className = "recaptcha-checkbox-border"
        )
    WebElement recaptchaWE;
    @FindBy(id = "register")
    WebElement registerWE;

    @Getter @Setter public boolean ready;

    public BookStoreRegistrationForm init(WebDriver driver, RegistrationFormVO registrationFormVO) {
        if (registrationFormVO == null || registrationFormVO.isEmpty()) {
            throw new RuntimeException("Registration form can't be empty");
        }

        this.driver = driver;
        this.registrationFormVO = registrationFormVO;
        this.ready = true;

        System.out.println("form has values: "+!registrationFormVO.isEmpty());

        return this;
    }

    /**
     * Initializes the page object.
     * Required to init before using this object.
     * 
     * NB PageFactory requires a no-argument constructor to instantiate the page object.
     */
   /* public BookStoreRegistrationForm init() {
        
        return PageFactory.initElements(driver, this.getClass());
    }*/

    /**
     * Registers a user.
     * NB call init() before trying to register
     */
    public void register() {
        driver.get("https://demoqa.com/register");
        if (!this.ready) { throw new RuntimeException("Registration form is not initialized"); }
        if (this.registrationFormVO == null || this.registrationFormVO.isEmpty()) {
            throw new RuntimeException("Registration form is empty");
        }
        firstNameWE.sendKeys(registrationFormVO.getFirstName());
        lastNameWE.sendKeys(registrationFormVO.getLastName());
        usernameWE.sendKeys(registrationFormVO.getUsername());
        passwordWE.sendKeys(registrationFormVO.getPassword());

        //switch to iframe, as selenium cant directly interact with elements inside it
        driver.switchTo().frame(driver.findElement(By.cssSelector("iframe[title='reCAPTCHA']")));
            recaptchaWE.click();
        driver.switchTo().defaultContent(); // Switch back to the main content
        
        /*try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }*/

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.elementToBeClickable(registerWE));
        registerWE.click();
    }

    public boolean locatedFirstName() { return firstNameWE != null; }
    public boolean locatedLastName() { return lastNameWE != null; }
    public boolean locatedUsername() { return usernameWE != null; }
    public boolean locatedPassword() { return passwordWE != null; }
    public boolean locatedRecaptcha() { return recaptchaWE != null; }
    public boolean locatedRegister() { return registerWE != null; }

}
