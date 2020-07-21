package com.drew.surfphotos.rest.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.xml.bind.annotation.XmlType;

@XmlType(name="")
@ApiModel("UploadPhotoResult")
public class UploadPhotoResultREST extends ImageLinkREST{

    @ApiModelProperty(required = true, value = "Photo id for uploaded photo")
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
