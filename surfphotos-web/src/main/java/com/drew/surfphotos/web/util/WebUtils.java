package com.drew.surfphotos.web.util;

import javax.servlet.http.HttpServletRequest;

public class WebUtils {

    public static boolean isAjaxRequest(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }
}
