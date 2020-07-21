package com.drew.surfphotos.rest.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.xml.bind.annotation.XmlType;

@XmlType(name = "")
@ApiModel("SimpleProfile")
public class SimpleProfileREST {

    @ApiModelProperty(required = true, value = "Profile id. Uses as unique identificator of profile via rest api")
    private Long id;

    @ApiModelProperty(required = true, value = "Profile uid. Can be useful if user wants to open profile via browser from his mobile application. Profile unique url will be http://surfphotos.com/${uid}")
    private String uid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
