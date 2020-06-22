package com.drew.surfphotos.web.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "CurrentRequestFilter", asyncSupported = true)
public class CurrentRequestFilter extends AbstractFilter {
    public static final String CURRENT_REQUEST_URL = "currentRequestUrl";

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setAttribute(CURRENT_REQUEST_URL, request.getRequestURI());
        chain.doFilter(request, response);
    }
}
