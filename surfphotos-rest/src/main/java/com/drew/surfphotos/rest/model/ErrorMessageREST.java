package com.drew.surfphotos.rest.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.xml.bind.annotation.XmlType;

@ApiModel("ErrorMessage")
public class ErrorMessageREST {

    @ApiModelProperty(required = true, value = "Error message. This message should be displayed to user if userError=true, otherwise it is message for developer")
    private String message;

    @ApiModelProperty(required = true, value = "Category of error message. If userError=true, message should displayed to user, otherwise it is message for developer")
    private boolean userError;

    public ErrorMessageREST() {
    }

    public ErrorMessageREST(String message) {
        this(message, false);
    }

    public ErrorMessageREST(String message, boolean userError) {
        this.message = message;
        this.userError = userError;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public boolean isUserError() {
        return userError;
    }

    public void setUserError(boolean userError) {
        this.userError = userError;
    }
}
