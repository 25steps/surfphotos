package com.drew.surfphotos.exception;

/**
 *Отвечает за все ошибки конфигурации, которые будут в нашем приложении
 */
public class ConfigException extends ApplicationException {
    public ConfigException(String message) {
        super(message);
    }

    public ConfigException(String message, Throwable cause) {
        super(message, cause);
    }
}
