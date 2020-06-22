package com.drew.surfphotos.exception;

/**
 * Ошибка выбрасывается если вдруг что-то пошло не так во время работы(некоректное поведение)
 */
public class InvalidWorkFlowException extends BusinessException {
    public InvalidWorkFlowException(String message) {
        super(message);
    }
}
