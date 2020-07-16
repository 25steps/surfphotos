package com.drew.surfphotos.rest.controller;

import com.drew.surfphotos.common.converter.ModelConverter;
import com.drew.surfphotos.model.Pageable;
import com.drew.surfphotos.model.domain.Photo;
import com.drew.surfphotos.model.domain.Profile;
import com.drew.surfphotos.rest.model.PhotosREST;
import com.drew.surfphotos.rest.model.ProfilePhotoREST;
import com.drew.surfphotos.rest.model.ProfileWithPhotosREST;
import com.drew.surfphotos.service.PhotoService;
import com.drew.surfphotos.service.ProfileService;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import java.util.List;

import static com.drew.surfphotos.rest.Constants.PHOTO_LIMIT;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/profile")
@Produces({APPLICATION_JSON})
@RequestScoped
public class PublicProfileController {

    @EJB
    private ProfileService profileService;

    @EJB
    private PhotoService photoService;

    @Inject
    private ModelConverter converter;

    @GET
    @Path("/{id}")
    public ProfileWithPhotosREST findProfile(
            @PathParam("id") Long id,
            @DefaultValue("false") @QueryParam("withPhotos") boolean withPhotos,
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
    public PhotosREST findProfilePhotos(
            @PathParam("profileId") Long profileId,
            @DefaultValue("1") @QueryParam("page") int page,
            @DefaultValue(PHOTO_LIMIT) @QueryParam("limit") int limit) {
        List<Photo> photos = photoService.findProfilePhotos(profileId, new Pageable(page, limit));
        return new PhotosREST(converter.convertList(photos, ProfilePhotoREST.class));
    }
}
