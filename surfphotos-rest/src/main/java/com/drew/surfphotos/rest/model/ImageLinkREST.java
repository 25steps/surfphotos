package com.drew.surfphotos.rest.model;

import javax.xml.bind.annotation.XmlType;

@XmlType(name="")
public class ImageLinkREST {

    private String url;

    public ImageLinkREST() {
    }

    public ImageLinkREST(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
