package com.drew.surfphotos.rest.model;

import com.drew.surfphotos.common.annotation.converter.ConvertAsURL;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.xml.bind.annotation.XmlType;

@XmlType(name="")
@ApiModel("ProfilePhoto")
public class ProfilePhotoREST {
    @ApiModelProperty(required = true, value = "Photo id. Uses as unique identificator for /preview and /download api")
    private Long id;

    @ConvertAsURL
    @ApiModelProperty(required = true)
    private String smallUrl;

    @ApiModelProperty(required = true)
    private long views;

    @ApiModelProperty(required = true)
    private long downloads;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSmallUrl() {
        return smallUrl;
    }

    public void setSmallUrl(String smallUrl) {
        this.smallUrl = smallUrl;
    }

    public long getViews() {
        return views;
    }

    public void setViews(long views) {
        this.views = views;
    }

    public long getDownloads() {
        return downloads;
    }

    public void setDownloads(long downloads) {
        this.downloads = downloads;
    }
}
