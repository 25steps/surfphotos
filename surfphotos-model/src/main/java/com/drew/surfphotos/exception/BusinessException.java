package com.drew.surfphotos.exception;

/**
 * абстрактный класс ошибок, который говорит, что в нашем приложении произошла ошибка связанная с бизнес правилами
 * пример: отсутствует объект, нарушена логика и тд
 * По сути это маркерный класс ошибок
 */
public abstract class BusinessException extends ApplicationException {
    public BusinessException(String message) {
        super(message, null, true, false);
    }
}
