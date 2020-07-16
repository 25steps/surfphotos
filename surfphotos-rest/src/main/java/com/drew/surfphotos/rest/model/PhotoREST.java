package com.drew.surfphotos.rest.model;

import javax.xml.bind.annotation.XmlType;

@XmlType(name="")
public class PhotoREST extends ProfilePhotoREST{
    private ProfileREST profile;

    public ProfileREST getProfile() {
        return profile;
    }

    public void setProfile(ProfileREST profile) {
        this.profile = profile;
    }
}
