package com.drew.surfphotos.rest.errormapper;

import com.drew.surfphotos.exception.ValidationException;
import com.drew.surfphotos.rest.model.ErrorMessageREST;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

@Provider
@ApplicationScoped
public class ValidationExceptionMapper implements ExceptionMapper<ValidationException> {

    @Override
    public Response toResponse(ValidationException exception) {
        return Response.
                status(BAD_REQUEST).
                entity(new ErrorMessageREST(exception.getMessage(), true)).
                type(MediaType.APPLICATION_JSON).
                build();
    }
}
