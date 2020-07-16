package com.drew.surfphotos.rest.model;

import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlType(name="")
public class ProfileWithPhotosREST extends ProfileREST{

    private List<? extends ProfilePhotoREST> photos;

    public List<? extends ProfilePhotoREST> getPhotos() {
        return photos;
    }

    public void setPhotos(List<? extends ProfilePhotoREST> photos) {
        this.photos = photos;
    }
}
