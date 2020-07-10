package com.drew.surfphotos.web.component;

import com.drew.surfphotos.exception.ApplicationException;

public class HttpStatusException extends ApplicationException {
    private final int status;
    public HttpStatusException(int status, String message) {
        super(message);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
