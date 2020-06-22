package com.drew.surfphotos.exception;

/**
 *Так как большая часть ошибок будет не проверяемая, то чтобы не заграмождать код try/catch
 * используется данный класс ошибок
 */
public class ApplicationException extends RuntimeException {
    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationException(Throwable cause) {
        super(cause);
    }

    public ApplicationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
