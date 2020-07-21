package com.drew.surfphotos.rest.controller;

import com.drew.surfphotos.common.converter.ModelConverter;
import com.drew.surfphotos.model.Pageable;
import com.drew.surfphotos.model.domain.Photo;
import com.drew.surfphotos.model.domain.Profile;
import com.drew.surfphotos.rest.model.ErrorMessageREST;
import com.drew.surfphotos.rest.model.PhotosREST;
import com.drew.surfphotos.rest.model.ProfilePhotoREST;
import com.drew.surfphotos.rest.model.ProfileWithPhotosREST;
import com.drew.surfphotos.service.PhotoService;
import com.drew.surfphotos.service.ProfileService;
import io.swagger.annotations.*;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;

import static com.drew.surfphotos.rest.Constants.PHOTO_LIMIT;
import static com.drew.surfphotos.rest.StatusMessages.INTERNAL_ERROR;
import static com.drew.surfphotos.rest.StatusMessages.SERVICE_UNAVAILABLE;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Api("profile")
@Path("/profile")
@Produces({APPLICATION_JSON})
@RequestScoped
@ApiResponses({
        @ApiResponse(code = 500, message = INTERNAL_ERROR, response = ErrorMessageREST.class),
        @ApiResponse(code = 502, message = SERVICE_UNAVAILABLE),
        @ApiResponse(code = 503, message = SERVICE_UNAVAILABLE),
        @ApiResponse(code = 504, message = SERVICE_UNAVAILABLE)
})
public class PublicProfileController {

    @EJB
    private ProfileService profileService;

    @EJB
    private PhotoService photoService;

    @Inject
    private ModelConverter converter;

    @GET
    @Path("/{id}")
    @ApiOperation(value = "Finds profile by id",
            notes = "withPhotos and limit are optional. If withPhotos=true, this method returns profile with his photos, limited by limit parameter")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Profile not found by id", response = ErrorMessageREST.class)
    })
    public ProfileWithPhotosREST findProfile(
            @ApiParam(value = "Profile number id", required = true)
            @PathParam("id") Long id,
            @ApiParam(value = "Flag, which adds or not profile photos to result")
            @DefaultValue("false") @QueryParam("withPhotos") boolean withPhotos,
            @ApiParam(value = "Photos limit")
            @DefaultValue(PHOTO_LIMIT) @QueryParam("limit") int limit) {
        Profile p = profileService.findById(id);
        ProfileWithPhotosREST profile = converter.convert(p, ProfileWithPhotosREST.class);
        if (withPhotos) {
            List<Photo> photos = photoService.findProfilePhotos(profile.getId(), new Pageable(1, limit));
            profile.setPhotos(converter.convertList(photos, ProfilePhotoREST.class));
        }
        return profile;
    }

    @GET
    @Path("/{profileId}/photos")
    @ApiOperation(value = "Finds profile photos",
            notes = "If profile not found by profileId, this method return empty list with status code 200")
    public PhotosREST findProfilePhotos(
            @ApiParam(value = "Profile number id", required = true)
            @PathParam("profileId") Long profileId,
            @ApiParam(value = "Pagination page (this parameter should start with 1)")
            @DefaultValue("1") @QueryParam("page") int page,
            @ApiParam(value = "Photos limit")
            @DefaultValue(PHOTO_LIMIT) @QueryParam("limit") int limit) {
        List<Photo> photos = photoService.findProfilePhotos(profileId, new Pageable(page, limit));
        return new PhotosREST(converter.convertList(photos, ProfilePhotoREST.class));
    }
}
