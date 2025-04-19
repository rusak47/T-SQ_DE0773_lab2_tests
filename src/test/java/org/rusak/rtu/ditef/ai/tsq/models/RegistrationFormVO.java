package org.rusak.rtu.ditef.ai.tsq.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @Builder @NoArgsConstructor @AllArgsConstructor @ToString
public class RegistrationFormVO {
    public enum Gender {
        MALE, FEMALE, OTHER
    }
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private Gender gender; 
    private String mobile;
    private String dateOfBirth;

    public boolean isEmpty() {
        return (this.firstName == null || this.firstName.isEmpty()) &&
               (this.lastName == null || this.lastName.isEmpty()) &&
               (this.username == null || this.username.isEmpty()) &&
               (this.email == null || this.email.isEmpty()) &&
               (this.password == null || this.password.isEmpty());
    }
}
