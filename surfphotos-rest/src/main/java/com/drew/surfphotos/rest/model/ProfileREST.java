package com.drew.surfphotos.rest.model;

import com.drew.surfphotos.common.annotation.converter.ConvertAsURL;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.xml.bind.annotation.XmlType;

@XmlType(name = "")
@ApiModel("Profile")
public class ProfileREST extends SimpleProfileREST{

    @ApiModelProperty(required = true)
    private String firstName;

    @ApiModelProperty(required = true)
    private String lastName;

    @ConvertAsURL
    @ApiModelProperty(required = true)
    private String avatarUrl;

    @ApiModelProperty(required = true)
    private String jobTitle;

    @ApiModelProperty(required = true)
    private String location;

    @ApiModelProperty(required = true)
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
