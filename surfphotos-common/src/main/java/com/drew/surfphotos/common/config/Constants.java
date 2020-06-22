package com.drew.surfphotos.common.config;

import java.util.concurrent.TimeUnit;

public final class Constants {
    public static final long MAX_UPLOADED_PHOTO_SIZE_IN_BYTES = 10*1024*1024; // 10mb
    public static final long DEFAULT_ASYNC_OPERATION_TIMEOUT_IN_MILES = TimeUnit.SECONDS.toMillis(30);
    private Constants(){}
}
