package com.drew.surfphotos.rest.model;

import com.drew.surfphotos.model.domain.Profile;
import com.drew.surfphotos.model.validation.EnglishLanguage;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlType;

@XmlType(name="")
public class SignUpProfileREST extends AuthentificationCodeREST{
    private String firstName;

    private String lastName;

    private String jobTitle;

    private String location;

    public SignUpProfileREST() {
    }

    @NotNull(message = "{Profile.firstName.NotNull}")
    @Size(min = 1, message = "{Profile.firstName.Size}")
    @EnglishLanguage(withNumbers = false, withPunctuations = false, withSpecialSymbols = false)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @NotNull(message = "{Profile.lastName.NotNull}")
    @Size(min = 1, message = "{Profile.lastName.Size}")
    @EnglishLanguage(withNumbers = false, withPunctuations = false, withSpecialSymbols = false)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @NotNull(message = "{Profile.jobTitle.NotNull}")
    @Size(min = 5, message = "{Profile.jobTitle.Size}")
    @EnglishLanguage(withSpecialSymbols = false)
    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    @NotNull(message = "{Profile.location.NotNull}")
    @Size(min = 5, message = "{Profile.location.Size}")
    @EnglishLanguage(withSpecialSymbols = false)
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void copyToProfile(Profile profile) {
        profile.setFirstName(getFirstName());
        profile.setLastName(getLastName());
        profile.setJobTitle(getJobTitle());
        profile.setLocation(getLocation());
    }
}
