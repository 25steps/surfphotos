package com.drew.surfphotos.exception;

/**
 * Нарушение правил доступа, если пользователь запрашивает ресурс на который он не авторизирован
 */
public class AccessForbiddenException extends BusinessException {
    public AccessForbiddenException(String message) {
        super(message);
    }
}
