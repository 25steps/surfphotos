package com.drew.surfphotos.rest.model;

import javax.xml.bind.annotation.XmlType;

@XmlType(name="")
public class SimpleProfileREST {

    private Long id;

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
