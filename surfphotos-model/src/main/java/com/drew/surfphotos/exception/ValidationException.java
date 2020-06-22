package com.drew.surfphotos.exception;

/**
 * Ошибка валидации данных, информация для пользователя, если он ошибся в заполнении данных
 */
public class ValidationException extends BusinessException {
    public ValidationException(String message) {
        super(message);
    }
}
