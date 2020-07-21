package com.drew.surfphotos.rest.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.xml.bind.annotation.XmlType;
import java.util.List;

@ApiModel("ValidationItem")
public class ValidationItemREST {

    @ApiModelProperty(required = true, value = "Parameter name")
    private String field;

    @ApiModelProperty(required = true, value = "String error message list")
    private List<String> messages;

    public ValidationItemREST() {
    }

    public ValidationItemREST(String field, List<String> messages) {
        this.field = field;
        this.messages = messages;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }
}
