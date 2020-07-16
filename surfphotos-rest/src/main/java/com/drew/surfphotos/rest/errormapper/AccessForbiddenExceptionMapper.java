package com.drew.surfphotos.rest.errormapper;

import com.drew.surfphotos.exception.AccessForbiddenException;
import com.drew.surfphotos.rest.model.ErrorMessageREST;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.core.Response.Status.FORBIDDEN;

@Provider
@ApplicationScoped
public class AccessForbiddenExceptionMapper implements ExceptionMapper<AccessForbiddenException> {

    @Override
    public Response toResponse(AccessForbiddenException exception) {
        return Response.
                status(FORBIDDEN).
                entity(new ErrorMessageREST(exception.getMessage())).
                type(MediaType.APPLICATION_JSON).
                build();
    }

}
