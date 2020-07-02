package com.drew.surfphotos.web.util;

import com.drew.surfphotos.exception.ValidationException;

public final class UrlExtractorUtils {

    public static String getPathVariableValue(String url, String preffix, String suffix) {
        if(url.length() >= preffix.length() + suffix.length() && url.startsWith(preffix) && url.endsWith(suffix)) {
            return url.substring(preffix.length(), url.length() - suffix.length());
        } else {
            throw new ValidationException(String.format("Can't extract path variable from url=%s with preffix=%s and suffuix=%s", url, preffix, suffix));
        }
    }

    private UrlExtractorUtils() {}
}
