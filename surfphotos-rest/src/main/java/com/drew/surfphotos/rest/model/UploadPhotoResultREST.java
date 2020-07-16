package com.drew.surfphotos.rest.model;

import javax.xml.bind.annotation.XmlType;

@XmlType(name="")
public class UploadPhotoResultREST extends ImageLinkREST{

    private Long id;

    public UploadPhotoResultREST(Long id, String url) {
        super(url);
        this.id = id;
    }

    public UploadPhotoResultREST() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
