package com.drew.surfphotos.rest.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.xml.bind.annotation.XmlType;

@XmlType(name="")
@ApiModel("ImageLink")
public class ImageLinkREST {

    @ApiModelProperty(required = true)
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
