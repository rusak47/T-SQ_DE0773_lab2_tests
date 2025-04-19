package org.rusak.rtu.ditef.ai.tsq.models;

import org.openqa.selenium.By;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor @ToString
public class RegistrationFormDOMVO {
    
    public enum Gender {
        MALE, FEMALE, OTHER
    }

    private String url;
    private String successUrl;
    
    private FormElement firstName;
    private FormElement lastName;
    private FormElement username;
    private FormElement email;
    private FormElement password;
    private FormElement passwordStrengthMeter;
    private FormElement confirmPassword;
    private FormElement gender;
    private FormElement mobile;
    private FormElement dateOfBirth;

    private FormElement logoutButton;
    private FormElement submitButton;

    @Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor
    public static class FormElement {
        private By locator;
        private By errorLocator;
        private String value;
        private boolean required;

        public FormElement(By locator, String value) {
            this(locator, null, value);
        }

        public FormElement(By locator, By errorLocator, String value) {
            this.locator = locator;
            this.errorLocator = errorLocator;
            this.value = value;
            this.required = true;
        }

        public String toString() {
            return "{value='" + value + "'}";
        }
    }


    public boolean hasUrl() {
        return this.url != null && !this.url.isEmpty();
    }
    public boolean hasSuccessUrl() {
        return this.successUrl != null && !this.successUrl.isEmpty();
    }

    public boolean isEmpty() {
        return (this.firstName == null || (this.firstName.getValue() == null || this.firstName.getValue().isEmpty()) && this.firstName.isRequired()) &&
               (this.lastName == null || (this.lastName.getValue() == null || this.lastName.getValue().isEmpty()) && this.lastName.isRequired()) &&
               (this.username == null || (this.username.getValue() == null || this.username.getValue().isEmpty()) && this.username.isRequired()) &&
               (this.email == null || (this.email.getValue() == null || this.email.getValue().isEmpty()) && this.email.isRequired()) &&
               (this.password == null || (this.password.getValue() == null || this.password.getValue().isEmpty()) && this.password.isRequired());
    }

    public boolean isReady() {
        return this.hasUrl() && this.hasSuccessUrl() && !this.isEmpty();
    }
}
