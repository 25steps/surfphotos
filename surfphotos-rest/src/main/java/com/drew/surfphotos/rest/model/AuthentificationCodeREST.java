package com.drew.surfphotos.rest.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.xml.bind.annotation.XmlType;

@ApiModel("AuthentificationCode")
public class AuthentificationCodeREST {
    private String code;

    @ApiModelProperty(required = true, value = "Authentification code retrieved from social service")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
