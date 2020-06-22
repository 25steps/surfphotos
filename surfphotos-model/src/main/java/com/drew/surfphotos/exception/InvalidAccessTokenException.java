package com.drew.surfphotos.exception;

/**
 *Ошибка будет возникать, если пользователь будет пытаться зайти не по валидному токену
 */
public class InvalidAccessTokenException extends BusinessException {
    public InvalidAccessTokenException(String message) {
        super(message);
    }
}
