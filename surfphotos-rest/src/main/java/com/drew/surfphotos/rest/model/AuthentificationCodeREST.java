package com.drew.surfphotos.rest.model;

import javax.xml.bind.annotation.XmlType;

@XmlType(name="")
public class AuthentificationCodeREST {
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
