package com.drew.surfphotos.exception;

/**
 * Выбрасывается при отсутствие объекта
 */
public class ObjectNotFoundException extends BusinessException {
    public ObjectNotFoundException(String message) {
        super(message);
    }
}
