package com.drew.surfphotos.rest.model;

import com.drew.surfphotos.common.annotation.converter.ConvertAsURL;

import javax.xml.bind.annotation.XmlType;

@XmlType(name="")
public class ProfileREST extends SimpleProfileREST{

    private String firstName;

    private String lastName;

    @ConvertAsURL
    private String avatarUrl;

    private String jobTitle;

    private String location;

    private int photoCount;

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

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getPhotoCount() {
        return photoCount;
    }

    public void setPhotoCount(int photoCount) {
        this.photoCount = photoCount;
    }
}
