package com.drew.surfphotos.rest.controller;

import com.drew.surfphotos.common.converter.ModelConverter;
import com.drew.surfphotos.common.converter.UrlConveter;
import com.drew.surfphotos.model.OriginalImage;
import com.drew.surfphotos.model.Pageable;
import com.drew.surfphotos.model.SortMode;
import com.drew.surfphotos.model.domain.Photo;
import com.drew.surfphotos.rest.model.ImageLinkREST;
import com.drew.surfphotos.rest.model.PhotoREST;
import com.drew.surfphotos.rest.model.PhotosREST;
import com.drew.surfphotos.service.PhotoService;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

import static com.drew.surfphotos.rest.Constants.PHOTO_LIMIT;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_OCTET_STREAM;

@Path("/photo")
@Produces({APPLICATION_JSON})
@RequestScoped
public class PhotoController {

    @EJB
    private PhotoService photoService;

    @Inject
    private UrlConveter urlConveter;

    @Inject
    private ModelConverter converter;

    @GET
    @Path("/all")
    public PhotosREST findAllPhotos(
            @DefaultValue("false") @QueryParam("withTotal") boolean withTotal,
            @DefaultValue("popular_photo") @QueryParam("sortMode") String sortMode,
            @DefaultValue("1") @QueryParam("page") int page,
            @DefaultValue(PHOTO_LIMIT) @QueryParam("limit") int limit) {
        List<Photo> photos = photoService.findPopularPhotos(SortMode.of(sortMode), new Pageable(page, limit));
        PhotosREST result = new PhotosREST();
        result.setPhotos(converter.convertList(photos, PhotoREST.class));
        if(withTotal) {
            result.setTotal(photoService.countAllPhotos());
        }
        return result;
    }

    @GET
    @Path("/preview/{id}")
    public ImageLinkREST viewLargePhoto(
            @PathParam("id") Long photoId) {
        String relativeUrl = photoService.viewLargePhoto(photoId);
        String absoluteUrl = urlConveter.convert(relativeUrl);
        return new ImageLinkREST(absoluteUrl);
    }

    @GET
    @Path("/download/{id}")
    @Produces(APPLICATION_OCTET_STREAM)
    public Response downloadOriginalImage(
            @PathParam("id") Long photoId) {
        OriginalImage originalImage = photoService.downloadOriginalImage(photoId);
        Response.ResponseBuilder builder = Response.ok(originalImage.getIn(), APPLICATION_OCTET_STREAM);
        builder.header("Content-Disposition", "attachment; filename=" + originalImage.getName());
        return builder.build();
    }
}
