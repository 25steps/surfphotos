package com.drew.surfphotos.rest.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.xml.bind.annotation.XmlType;

@ApiModel("UpdateProfile")
public class UpdateProfileREST extends SignUpProfileREST{

    @Override
    @ApiModelProperty(hidden = true)
    public String getCode() {
        return super.getCode();
    }
}
