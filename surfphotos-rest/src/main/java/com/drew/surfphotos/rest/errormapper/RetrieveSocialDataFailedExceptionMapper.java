package com.drew.surfphotos.rest.errormapper;

import com.drew.surfphotos.exception.RetrieveSocialDataFailedException;
import com.drew.surfphotos.rest.model.ErrorMessageREST;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.drew.surfphotos.rest.StatusMessages.INTERNAL_ERROR;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

@Provider
@ApplicationScoped
public class RetrieveSocialDataFailedExceptionMapper implements ExceptionMapper<RetrieveSocialDataFailedException> {

    @Inject
    private Logger logger;

    @Override
    public Response toResponse(RetrieveSocialDataFailedException exception) {
        logger.log(Level.SEVERE, INTERNAL_ERROR, exception);
        return Response.
                status(UNAUTHORIZED).
                entity(new ErrorMessageREST(exception.getMessage())).
                type(MediaType.APPLICATION_JSON).
                build();
    }
}
