package com.drew.surfphotos.rest.errormapper;

import com.drew.surfphotos.exception.ObjectNotFoundException;
import com.drew.surfphotos.rest.model.ErrorMessageREST;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Provider
@ApplicationScoped
public class ObjectNotFoundExceptionMapper implements ExceptionMapper<ObjectNotFoundException> {

    @Override
    public Response toResponse(ObjectNotFoundException exception) {
        return Response.
                status(NOT_FOUND).
                entity(new ErrorMessageREST(exception.getMessage())).
                type(MediaType.APPLICATION_JSON).
                build();
    }
}
