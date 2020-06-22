package com.drew.surfphotos.model;

/**
 * Чтобы загрузить новую фотку или аватарку, все эти операции будут выполняться с помощью асинхронных сервлетов, поэтому
 * нам нужен объект модели, который будет представлять нашу асинхронную операцию
 */
public interface AsyncOperation<T> {
    long getTimeOutInMillis(); // возвращает таймаут в милисекундах

    void onSuccess(T result); // в случае успеха result = success

    void onFailed(Throwable throwable); // в случае неудачи ошибка
}
