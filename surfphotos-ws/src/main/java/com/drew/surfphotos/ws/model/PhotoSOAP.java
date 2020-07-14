package com.drew.surfphotos.ws.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "photo")
@XmlAccessorType(XmlAccessType.FIELD)
public class PhotoSOAP extends ProfilePhotoSOAP {
    private ProfileSOAP profile;

    public ProfileSOAP getProfile() {
        return profile;
    }

    public void setProfile(ProfileSOAP profile) {
        this.profile = profile;
    }
}
