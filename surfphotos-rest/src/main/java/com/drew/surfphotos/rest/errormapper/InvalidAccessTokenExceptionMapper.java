package com.drew.surfphotos.rest.errormapper;

import com.drew.surfphotos.exception.InvalidAccessTokenException;
import com.drew.surfphotos.rest.model.ErrorMessageREST;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static com.drew.surfphotos.rest.StatusMessages.INVALID_ACCESS_TOKEN;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

@Provider
@ApplicationScoped
public class InvalidAccessTokenExceptionMapper implements ExceptionMapper<InvalidAccessTokenException> {

    @Override
    public Response toResponse(InvalidAccessTokenException exception) {
        return Response.
                status(UNAUTHORIZED).
                entity(new ErrorMessageREST(INVALID_ACCESS_TOKEN)).
                type(MediaType.APPLICATION_JSON).
                build();
    }
}
