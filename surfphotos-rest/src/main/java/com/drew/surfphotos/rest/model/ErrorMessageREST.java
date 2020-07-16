package com.drew.surfphotos.rest.model;

import javax.xml.bind.annotation.XmlType;

@XmlType(name="")
public class ErrorMessageREST {

    private String message;

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
