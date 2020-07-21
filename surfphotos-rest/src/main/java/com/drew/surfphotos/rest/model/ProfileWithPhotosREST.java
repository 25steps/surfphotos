package com.drew.surfphotos.rest.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlType(name = "")
@ApiModel("ProfileWithPhotos")
public class ProfileWithPhotosREST extends ProfileREST{

    @ApiModelProperty(required = true, value = "Photo list")
    private List<? extends ProfilePhotoREST> photos;

    public List<? extends ProfilePhotoREST> getPhotos() {
        return photos;
    }

    public void setPhotos(List<? extends ProfilePhotoREST> photos) {
        this.photos = photos;
    }
}
