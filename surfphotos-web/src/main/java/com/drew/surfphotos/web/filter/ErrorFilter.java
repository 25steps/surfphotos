package com.drew.surfphotos.web.filter;

import com.drew.surfphotos.exception.BusinessException;
import com.drew.surfphotos.web.component.ExceptionConverter;
import com.drew.surfphotos.web.component.HttpStatusException;
import com.drew.surfphotos.web.model.ErrorModel;
import com.drew.surfphotos.web.util.WebUtils;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.drew.surfphotos.web.Constants.EMPTY_MESSAGE;
import static com.drew.surfphotos.web.util.RoutingUtils.forwardToPage;
import static com.drew.surfphotos.web.util.RoutingUtils.sendJson;

@WebFilter(filterName = "ErrorFilter", asyncSupported = true)
public class ErrorFilter extends AbstractFilter {

    @Inject
    private Logger logger;

    @Inject
    private ExceptionConverter exceptionConverter;

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(request, new ThrowExceptionInsteadOfSendErrorResponse(response));
        } catch (Throwable th) {
            Throwable throwable = unWrapThrowable(th);
            logError(request, throwable);
            ErrorModel errorModel = exceptionConverter.convertToHttpStatus(throwable);
            handleError(request, errorModel, response);
        }
    }

    private Throwable unWrapThrowable(Throwable th) {
        if (th instanceof ServletException && th.getCause() != null) {
            return th.getCause();
        } else {
            return th;
        }
    }

    private void logError(HttpServletRequest request, Throwable th) {
        String errorMessage = String.format("Can't process request: %s -> %s", request.getRequestURI(), th.getMessage());
        if (th instanceof BusinessException) {
            logger.log(Level.WARNING, errorMessage);
        } else {
            logger.log(Level.SEVERE, errorMessage, th);
        }
    }

    private void handleError(HttpServletRequest request, ErrorModel errorModel, HttpServletResponse response) throws ServletException, IOException {
        response.reset();
        response.setStatus(errorModel.getStatus());
        if (WebUtils.isAjaxRequest(request)) {
            sendAjaxJsonErrorResponse(errorModel, request, response);
        } else {
            sendErrorPage(errorModel, request, response);
        }
    }

    private void sendAjaxJsonErrorResponse(ErrorModel errorModel, HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonObject json = Json.createObjectBuilder().add("success", false).add("error", errorModel.getMessage()).build();
        sendJson(json, request, response);
    }

    private void sendErrorPage(ErrorModel errorModel, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setAttribute("errorModel", errorModel);
        forwardToPage("error", request, response);
    }


    private static class ThrowExceptionInsteadOfSendErrorResponse extends HttpServletResponseWrapper {

        public ThrowExceptionInsteadOfSendErrorResponse(HttpServletResponse response) {
            super(response);
        }

        @Override
        public void sendError(int sc) throws IOException {
            sendError(sc, EMPTY_MESSAGE);
        }

        @Override
        public void sendError(int sc, String msg) throws IOException {
            throw new HttpStatusException(sc, msg);
        }
    }
}
