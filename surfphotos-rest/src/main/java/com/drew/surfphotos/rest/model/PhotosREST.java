package com.drew.surfphotos.rest.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlType(name = "")
@ApiModel("Photos")
public class PhotosREST {

    @ApiModelProperty(required = true, value = "Photo list")
    private List<? extends ProfilePhotoREST> photos;

    @ApiModelProperty(required = false, value = "Total photo count")
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
