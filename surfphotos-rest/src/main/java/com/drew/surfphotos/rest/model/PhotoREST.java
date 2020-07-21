package com.drew.surfphotos.rest.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.xml.bind.annotation.XmlType;

@XmlType(name="")
@ApiModel("Photo")
public class PhotoREST extends ProfilePhotoREST{
    @ApiModelProperty(required = true)
    private ProfileREST profile;

    public ProfileREST getProfile() {
        return profile;
    }

    public void setProfile(ProfileREST profile) {
        this.profile = profile;
    }
}
