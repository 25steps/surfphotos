package com.drew.surfphotos.web.controller.logged;

import com.drew.surfphotos.model.AsyncOperation;
import com.drew.surfphotos.model.domain.Profile;
import com.drew.surfphotos.web.model.PartImageResource;
import com.drew.surfphotos.web.security.SecurityUtils;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.drew.surfphotos.common.config.Constants.DEFAULT_ASYNC_OPERATION_TIMEOUT_IN_MILLIS;
import static com.drew.surfphotos.web.util.RoutingUtils.sendFileUploaderJson;

public abstract class AbstractUploadController<T> extends HttpServlet {

    @Inject
    protected Logger logger;

    @Override
    protected final void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Part part = req.getPart("qqfile");
        Profile profile = SecurityUtils.getCurrentProfile();
        final AsyncContext asyncContext = req.startAsync(req, resp);
        asyncContext.setTimeout(DEFAULT_ASYNC_OPERATION_TIMEOUT_IN_MILLIS);
        uploadImage(profile, new PartImageResource(part), new AsyncOperation<T>() {
            @Override
            public void onSuccess(T result) {
                handleSuccess(asyncContext, result);
            }

            @Override
            public void onFailed(Throwable throwable) {
                handleFailed(throwable, asyncContext);
            }

            @Override
            public long getTimeOutInMillis() {
                return asyncContext.getTimeout();
            }
        });
    }

    protected abstract void uploadImage(Profile profile, PartImageResource partImageResource, AsyncOperation<T> asyncOperation);

    protected void handleSuccess(AsyncContext asyncContext, T result) {
        try {
            JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder().add("success", true);
            for (Map.Entry<String, String> entry : getResultMap(result, (HttpServletRequest) asyncContext.getRequest()).entrySet()) {
                jsonObjectBuilder.add(entry.getKey(), entry.getValue());
            }
            JsonObject json = jsonObjectBuilder.build();
            sendJsonResponse(json, asyncContext);
        } finally {
            asyncContext.complete();
        }
    }

    protected void handleFailed(Throwable throwable, AsyncContext asyncContext) {
        try {
            logger.log(Level.SEVERE, "Async operation failed: " + throwable.getMessage(), throwable);
            JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder().add("success", false);
            JsonObject json = jsonObjectBuilder.build();
            sendJsonResponse(json, asyncContext);
        } finally {
            asyncContext.complete();
        }
    }

    protected void sendJsonResponse(JsonObject json, AsyncContext asyncContext) {
        try {
            HttpServletRequest request = (HttpServletRequest) asyncContext.getRequest();
            HttpServletResponse response = (HttpServletResponse) asyncContext.getResponse();
            // https://docs.fineuploader.com/endpoint_handlers/traditional.html#response
            sendFileUploaderJson(json, request, response);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "sendJsonResponse failed: " + e.getMessage(), e);
        }
    }

    protected abstract Map<String, String> getResultMap(T result, HttpServletRequest request);
}
