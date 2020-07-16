package com.drew.surfphotos.rest.controller;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.MULTIPART_FORM_DATA;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static com.drew.surfphotos.common.config.Constants.DEFAULT_ASYNC_OPERATION_TIMEOUT_IN_MILLIS;
import static com.drew.surfphotos.rest.Constants.ACCESS_TOKEN_HEADER;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Response;

import com.drew.surfphotos.common.converter.UrlConveter;
import com.drew.surfphotos.model.AsyncOperation;
import com.drew.surfphotos.model.domain.Photo;
import com.drew.surfphotos.model.domain.Profile;
import com.drew.surfphotos.rest.converter.ConstraintViolationConverter;
import com.drew.surfphotos.rest.model.ImageLinkREST;
import com.drew.surfphotos.rest.model.SignUpProfileREST;
import com.drew.surfphotos.rest.model.UpdateProfileREST;
import com.drew.surfphotos.rest.model.UploadImageREST;
import com.drew.surfphotos.rest.model.UploadPhotoResultREST;
import com.drew.surfphotos.rest.model.ValidationResultREST;
import com.drew.surfphotos.service.AccessTokenService;
import com.drew.surfphotos.service.PhotoService;
import com.drew.surfphotos.service.ProfileService;

@Path("/profile")
@Produces({APPLICATION_JSON})
@RequestScoped
public class ProtectedProfileController {

    @EJB
    private AccessTokenService accessTokenService;

    @EJB
    private ProfileService profileService;

    @EJB
    private PhotoService photoService;

    @Resource(lookup = "java:comp/Validator")
    private Validator validator;

    @Inject
    private ConstraintViolationConverter constraintViolationConverter;

    @Inject
    private UrlConveter urlConveter;

    @PUT
    @Path("/{id}")
    @Consumes(APPLICATION_JSON)
    public Response updateProfile(
            @PathParam("id") Long id,
            @HeaderParam(ACCESS_TOKEN_HEADER) String accessToken,
            UpdateProfileREST updateProfile) {
        Profile profile = accessTokenService.findProfile(accessToken, id);
        Set<ConstraintViolation<SignUpProfileREST>> violations = validator.validate(updateProfile, Default.class);
        if (violations.isEmpty()) {
            updateProfile.copyToProfile(profile);
            profileService.update(profile);
            return Response.ok().build();
        } else {
            ValidationResultREST validationResult = constraintViolationConverter.convert(violations);
            return Response.status(BAD_REQUEST).entity(validationResult).build();
        }
    }

    @POST
    @Path("/{id}/avatar")
    @Consumes(MULTIPART_FORM_DATA)
    public void uploadAvatar(
            @Suspended final AsyncResponse asyncResponse,
            @PathParam("id") Long id,
            @HeaderParam(ACCESS_TOKEN_HEADER) String accessToken,
            UploadImageREST uploadImage) throws Exception {
        Profile profile = accessTokenService.findProfile(accessToken, id);
        asyncResponse.setTimeout(DEFAULT_ASYNC_OPERATION_TIMEOUT_IN_MILLIS, TimeUnit.MILLISECONDS);
        profileService.uploadNewAvatar(profile, uploadImage.getImageResource(), new AsyncOperation<Profile>() {
            @Override
            public void onSuccess(Profile result) {
                String absoluteUrl = urlConveter.convert(result.getAvatarUrl());
                asyncResponse.resume(new ImageLinkREST(absoluteUrl));
            }
            @Override
            public void onFailed(Throwable throwable) {
                asyncResponse.resume(throwable);
            }

            @Override
            public long getTimeOutInMillis() {
                return DEFAULT_ASYNC_OPERATION_TIMEOUT_IN_MILLIS;
            }
        });
    }

    @POST
    @Path("/{id}/photo")
    @Consumes(MULTIPART_FORM_DATA)
    public void uploadPhoto(
            @Suspended final AsyncResponse asyncResponse,
            @PathParam("id") Long id,
            @HeaderParam(ACCESS_TOKEN_HEADER) String accessToken,
            UploadImageREST uploadImage) throws Exception {
        Profile profile = accessTokenService.findProfile(accessToken, id);
        asyncResponse.setTimeout(DEFAULT_ASYNC_OPERATION_TIMEOUT_IN_MILLIS, TimeUnit.MILLISECONDS);
        photoService.uploadNewPhoto(profile, uploadImage.getImageResource(), new AsyncOperation<Photo>() {
            @Override
            public void onSuccess(Photo result) {
                String absoluteUrl = urlConveter.convert(result.getSmallUrl());
                asyncResponse.resume(new UploadPhotoResultREST(result.getId(), absoluteUrl));
            }
            @Override
            public void onFailed(Throwable throwable) {
                asyncResponse.resume(throwable);
            }
            @Override
            public long getTimeOutInMillis() {
                return DEFAULT_ASYNC_OPERATION_TIMEOUT_IN_MILLIS;
            }
        });
    }
}
