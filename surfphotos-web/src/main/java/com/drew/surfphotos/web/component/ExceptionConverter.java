package com.drew.surfphotos.web.component;

import com.drew.surfphotos.web.model.ErrorModel;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Map;

import static com.drew.surfphotos.web.Constants.DEFAULT_ERROR_MESSAGE;
import static com.drew.surfphotos.web.Constants.EMPTY_MESSAGE;
import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

@ApplicationScoped
public class ExceptionConverter {

    @Inject
    private Map<Class, Integer> statusCodeMap;

    @Inject
    private Map<Integer, String> statusMessagesMap;

    public ErrorModel convertToHttpStatus(Throwable throwable) {
        if (throwable instanceof HttpStatusException) {
            HttpStatusException ex = (HttpStatusException) throwable;
            return createErrorModel(ex.getStatus(), ex.getMessage());
        } else {
            Integer status = statusCodeMap.getOrDefault(throwable.getClass(), SC_INTERNAL_SERVER_ERROR);
            return createErrorModel(status, throwable.getMessage());
        }
    }

    private ErrorModel createErrorModel(int status, String msg) {
        if (status == SC_INTERNAL_SERVER_ERROR) {
            return new ErrorModel(status, DEFAULT_ERROR_MESSAGE);
        } else if (EMPTY_MESSAGE.equals(msg)) {
            return new ErrorModel(status, statusMessagesMap.getOrDefault(status, DEFAULT_ERROR_MESSAGE));
        } else {
            return new ErrorModel(status, msg);
        }
    }
}
