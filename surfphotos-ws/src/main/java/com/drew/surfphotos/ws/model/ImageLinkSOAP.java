package com.drew.surfphotos.ws.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "imageLink")
@XmlAccessorType(XmlAccessType.FIELD)
public class ImageLinkSOAP {
    private String url;

    public ImageLinkSOAP(String url) {
        this.url = url;
    }

    public ImageLinkSOAP() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
