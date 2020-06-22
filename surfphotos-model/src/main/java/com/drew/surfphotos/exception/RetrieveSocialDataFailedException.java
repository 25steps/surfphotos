package com.drew.surfphotos.exception;

/**
 * Данный тип ошибки выбрасывается, если пользователь пытается зайти через соц сети и во время аутентификации происходит
 * сбой
 */
public class RetrieveSocialDataFailedException extends ApplicationException {
    public RetrieveSocialDataFailedException(String message) {
        super(message);
    }

    public RetrieveSocialDataFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
