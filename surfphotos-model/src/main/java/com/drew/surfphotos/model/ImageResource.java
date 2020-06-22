package com.drew.surfphotos.model;

import javax.validation.Path;

/**
 * Объект модели, который представляет собой медиа данные
 */
public interface ImageResource extends AutoCloseable{

    Path getTempPath(); // промежуточное хранение информации, InputStream не подходит так как некоректно работают с утилитами ресайза
    @Override
    void close() throws Exception;
}
