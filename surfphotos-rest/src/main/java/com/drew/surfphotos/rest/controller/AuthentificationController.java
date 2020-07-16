package com.drew.surfphotos.rest.controller;

import com.drew.surfphotos.common.converter.ModelConverter;
import com.drew.surfphotos.common.annotation.qualifier.Facebook;
import com.drew.surfphotos.common.annotation.qualifier.GooglePlus;
import com.drew.surfphotos.common.annotation.group.SignUpGroup;
import com.drew.surfphotos.exception.RetrieveSocialDataFailedException;
import com.drew.surfphotos.model.domain.AccessToken;
import com.drew.surfphotos.model.domain.Profile;
import static com.drew.surfphotos.rest.Constants.ACCESS_TOKEN_HEADER;
import com.drew.surfphotos.rest.converter.ConstraintViolationConverter;
import com.drew.surfphotos.rest.model.AuthentificationCodeREST;
import com.drew.surfphotos.rest.model.ProfileREST;
import com.drew.surfphotos.rest.model.SignUpProfileREST;
import com.drew.surfphotos.rest.model.SimpleProfileREST;
import com.drew.surfphotos.rest.model.ValidationResultREST;
import com.drew.surfphotos.service.AccessTokenService;
import com.drew.surfphotos.service.ProfileService;
import com.drew.surfphotos.service.SocialService;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;
import java.util.Set;
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
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.OK;



@Path("/auth")
@Produces(APPLICATION_JSON)
@RequestScoped
public class AuthentificationController {

    @Inject
    @GooglePlus
    private SocialService googplePlusSocialService;

    @Inject
    @Facebook
    private SocialService facebookSocialService;

    @EJB
    private ProfileService profileService;

    @EJB
    private AccessTokenService accessTokenService;

    @Inject
    private ModelConverter converter;

    @Resource(lookup = "java:comp/Validator")
    private Validator validator;

    @Inject
    private ConstraintViolationConverter constraintViolationConverter;

    @POST
    @Path("/sign-in/facebook")
    @Consumes(APPLICATION_JSON)
    public Response facebookSignIn(AuthentificationCodeREST authentificationCode) {
        return auth(authentificationCode, facebookSocialService);
    }

    @POST
    @Path("/sign-up/facebook")
    @Consumes(APPLICATION_JSON)
    public Response facebookSignUp(SignUpProfileREST signUpProfile) {
        return auth(signUpProfile, facebookSocialService);
    }

    @POST
    @Path("/sign-in/google-plus")
    @Consumes(APPLICATION_JSON)
    public Response googplePlusSignIn(AuthentificationCodeREST authentificationCode) {
        return auth(authentificationCode, googplePlusSocialService);
    }

    @POST
    @Path("/sign-up/google-plus")
    @Consumes(APPLICATION_JSON)
    public Response googplePlusSignUp(SignUpProfileREST signUpProfile) {
        return auth(signUpProfile, googplePlusSocialService);
    }

    @POST
    @Path("/sign-out")
    public Response signOut(
            @HeaderParam(ACCESS_TOKEN_HEADER) String token) {
        accessTokenService.invalidateAccessToken(token);
        return Response.ok().build();
    }

    protected Response auth(AuthentificationCodeREST model, SocialService socialService) {
        validateCode(model.getCode());
        Profile profile = socialService.fetchProfile(model.getCode());
        Optional<Profile> profileOptional = profileService.findByEmail(profile.getEmail());
        if(profileOptional.isPresent()) {
            Profile signedInProfile = profileOptional.get();
            AccessToken accessToken = accessTokenService.generateAccessToken(signedInProfile);
            return buidResponse(OK, signedInProfile, Optional.of(accessToken.getToken()), SimpleProfileREST.class);
        } else if(model instanceof SignUpProfileREST) {
            return processSignUp((SignUpProfileREST)model);
        } else {
            profileService.translitSocialProfile(profile);
            return buidResponse(NOT_FOUND, profile, Optional.empty(), ProfileREST.class);
        }
    }

    protected Response buidResponse(Status status, Profile profile, Optional<String> accessToken, Class<?> resultClass) {
        Response.ResponseBuilder builder = Response.status(status);
        builder.entity(converter.convert(profile, resultClass));
        if(accessToken.isPresent()) {
            builder.header(ACCESS_TOKEN_HEADER, accessToken.get());
        }
        return builder.build();
    }

    protected void validateCode(String code) {
        if(StringUtils.isBlank(code)) {
            throw new RetrieveSocialDataFailedException("Code is required");
        }
    }

    protected Response processSignUp(SignUpProfileREST signUpProfile) {
        Set<ConstraintViolation<SignUpProfileREST>> violations = validator.validate(signUpProfile, Default.class);
        if(violations.isEmpty()) {
            Profile profile = new Profile();
            signUpProfile.copyToProfile(profile);
            profileService.signUp(profile, true);
            AccessToken accessToken = accessTokenService.generateAccessToken(profile);
            return buidResponse(OK, profile, Optional.of(accessToken.getToken()), SimpleProfileREST.class);
        } else{
            ValidationResultREST validationResult = constraintViolationConverter.convert(violations);
            return Response.status(BAD_REQUEST).entity(validationResult).build();
        }
    }
}
