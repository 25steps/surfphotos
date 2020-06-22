package com.drew.surfphotos.web.util;

import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

public class RoutingUtils {

    public static void forwardToPage(String pageName, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("currentPage", String.format("../view/%s.jsp", pageName));
        request.getRequestDispatcher("/WEB-INF/template/page-template.jsp").forward(request, response);
    }

    public static void forwardToFragment(String fragmentName, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(String.format("/WEB-INF/fragment/%s.jsp", fragmentName)).forward(request, response);
    }

    public static void redirectToUrl(String url, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(url);
    }

    public static void sendJson(JsonObject json, HttpServletRequest request, HttpServletResponse response) throws IOException {
        sendJson("application/json", json, request, response);
    }

    private static void sendJson(String contentType, JsonObject json, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String content = json.toString();
        int length = content.getBytes(StandardCharsets.UTF_8).length;
        response.setContentType(contentType);
        response.setContentLength(length);

        try (Writer wr = response.getWriter()) {
            wr.write(content);
            wr.flush();
        }
    }

    private RoutingUtils() {
    }
}
