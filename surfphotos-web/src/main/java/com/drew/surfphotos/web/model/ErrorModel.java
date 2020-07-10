package com.drew.surfphotos.web.model;

public class ErrorModel {
    private final int status;
    private final String message;

    public ErrorModel(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
