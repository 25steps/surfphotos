package com.drew.surfphotos.rest.errormapper;

import com.drew.surfphotos.rest.model.ErrorMessageREST;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.logging.Level;
import java.util.logging.Logger;

import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static com.drew.surfphotos.rest.StatusMessages.INTERNAL_ERROR;

@Provider
@ApplicationScoped
public class DefaultExceptionMapper implements ExceptionMapper<Throwable> {

    @Inject
    private Logger logger;

    @Override
    public Response toResponse(Throwable exception) {
        logger.log(Level.SEVERE, INTERNAL_ERROR, exception);
        return Response.
                status(INTERNAL_SERVER_ERROR).
                entity(new ErrorMessageREST(INTERNAL_ERROR)).
                type(MediaType.APPLICATION_JSON).
                build();
    }
}
