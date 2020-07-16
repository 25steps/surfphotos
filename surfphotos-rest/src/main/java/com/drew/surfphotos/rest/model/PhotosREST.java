package com.drew.surfphotos.rest.model;

import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlType(name="")
public class PhotosREST {

    private List<? extends ProfilePhotoREST> photos;

    private Long total;

    public PhotosREST(List<? extends ProfilePhotoREST> photos) {
        this.photos = photos;
    }

    public PhotosREST() {
    }

    public List<? extends ProfilePhotoREST> getPhotos() {
        return photos;
    }

    public void setPhotos(List<? extends ProfilePhotoREST> photos) {
        this.photos = photos;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}
