package com.umedia.CloudGate.model;

//import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class RegistrationForm {

    public static final String FIELD_NAME_EMAIL = "email";

    private String email;

    private String firstName;

    private String lastName;
    
    private String username;

    private String password;

    private String passwordVerification;

    private SocialMediaProviders signInProvider;

    public RegistrationForm() {

    }

    //is signInProvider is not null, that is a social sign in 
    public boolean isNormalRegistration() {
        return signInProvider == null;
    }

    public boolean isSocialSignIn() {
        return signInProvider != null;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordVerification() {
        return passwordVerification;
    }

    public void setPasswordVerification(String passwordVerification) {
        this.passwordVerification = passwordVerification;
    }

    public SocialMediaProviders getSignInProvider() {
        return signInProvider;
    }

    public void setSignInProvider(SocialMediaProviders signInProvider) {
        this.signInProvider = signInProvider;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("email", email)
                .append("firstName", firstName)
                .append("lastName", lastName)
                .append("signInProvider", signInProvider)
                .toString();
    }
}
