package com.drew.surfphotos.web.component;

import com.drew.surfphotos.exception.AccessForbiddenException;
import com.drew.surfphotos.exception.InvalidAccessTokenException;
import com.drew.surfphotos.exception.ObjectNotFoundException;
import com.drew.surfphotos.exception.ValidationException;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.drew.surfphotos.web.Constants.DEFAULT_ERROR_MESSAGE;
import static javax.servlet.http.HttpServletResponse.*;

@Dependent
public class ExceptionMappingProducer {

    @Produces
    public Map<Class, Integer> getExceptionToStatusCodeMapping() {
        Map<Class<? extends Throwable>, Integer> map = new HashMap<>();
        map.put(ObjectNotFoundException.class, SC_NOT_FOUND);
        map.put(ValidationException.class, SC_BAD_REQUEST);
        map.put(AccessForbiddenException.class, SC_FORBIDDEN);
        map.put(InvalidAccessTokenException.class, SC_UNAUTHORIZED);
        //add mapping here
        return Collections.unmodifiableMap(map);
    }

    @Produces
    public Map<Integer, String> getStatusMessagesMapping() {
        Map<Integer, String> map = new HashMap<>();
        map.put(SC_BAD_REQUEST, "Current request is invalid");
        map.put(SC_UNAUTHORIZED, "Authentication required");
        map.put(SC_FORBIDDEN, "Requested operation not permitted");
        map.put(SC_NOT_FOUND, "Requested resource not found");
        map.put(SC_INTERNAL_SERVER_ERROR, DEFAULT_ERROR_MESSAGE);
        return map;
    }
}
