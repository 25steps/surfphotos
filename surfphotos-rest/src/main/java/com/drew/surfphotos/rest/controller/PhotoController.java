package com.drew.surfphotos.rest.controller;

import com.drew.surfphotos.common.converter.ModelConverter;
import com.drew.surfphotos.common.converter.UrlConveter;
import com.drew.surfphotos.model.OriginalImage;
import com.drew.surfphotos.model.Pageable;
import com.drew.surfphotos.model.SortMode;
import com.drew.surfphotos.model.domain.Photo;
import com.drew.surfphotos.rest.model.ErrorMessageREST;
import com.drew.surfphotos.rest.model.ImageLinkREST;
import com.drew.surfphotos.rest.model.PhotoREST;
import com.drew.surfphotos.rest.model.PhotosREST;
import com.drew.surfphotos.service.PhotoService;
import io.swagger.annotations.*;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

import static com.drew.surfphotos.rest.Constants.PHOTO_LIMIT;
import static com.drew.surfphotos.rest.StatusMessages.INTERNAL_ERROR;
import static com.drew.surfphotos.rest.StatusMessages.SERVICE_UNAVAILABLE;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_OCTET_STREAM;

@Api("photo")
@Path("/photo")
@Produces({APPLICATION_JSON})
@RequestScoped
@ApiResponses({
        @ApiResponse(code = 500, message = INTERNAL_ERROR, response = ErrorMessageREST.class),
        @ApiResponse(code = 502, message = SERVICE_UNAVAILABLE),
        @ApiResponse(code = 503, message = SERVICE_UNAVAILABLE),
        @ApiResponse(code = 504, message = SERVICE_UNAVAILABLE)
})
public class PhotoController {

    @EJB
    private PhotoService photoService;

    @Inject
    private UrlConveter urlConveter;

    @Inject
    private ModelConverter converter;

    @GET
    @Path("/all")
    @ApiOperation(value = "Gets popular photos according to given sortMode",
            notes = "If you need total photo count set withTotal=true")
    public PhotosREST findAllPhotos(
            @ApiParam(value = "Flag, which indicates to calculate or not total photo count")
            @DefaultValue("false") @QueryParam("withTotal") boolean withTotal,
            @ApiParam(value = "Photos sort mode", allowableValues = "popular_photo,popular_author")
            @DefaultValue("popular_photo") @QueryParam("sortMode") String sortMode,
            @ApiParam(value = "Pagination page (this parameter should start with 1)")
            @DefaultValue("1") @QueryParam("page") int page,
            @ApiParam(value = "Photos limit")
            @DefaultValue(PHOTO_LIMIT) @QueryParam("limit") int limit) {
        List<Photo> photos = photoService.findPopularPhotos(SortMode.of(sortMode), new Pageable(page, limit));
        PhotosREST result = new PhotosREST();
        result.setPhotos(converter.convertList(photos, PhotoREST.class));
        if (withTotal) {
            result.setTotal(photoService.countAllPhotos());
        }
        return result;
    }

    @GET
    @Path("/preview/{id}")
    @ApiOperation(value = "Gets large photo url by id",
            notes = "FYI: This method increments view count for given photo")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Photo not found by id", response = ErrorMessageREST.class)
    })
    public ImageLinkREST viewLargePhoto(
            @ApiParam(value = "Photo number id", required = true)
            @PathParam("id") Long photoId) {
        String relativeUrl = photoService.viewLargePhoto(photoId);
        String absoluteUrl = urlConveter.convert(relativeUrl);
        return new ImageLinkREST(absoluteUrl);
    }

    @GET
    @Path("/download/{id}")
    @Produces(APPLICATION_OCTET_STREAM)
    @ApiOperation(value = "Downloads original photo by id",
            notes = "FYI: This method increments download count for given photo")
    @ApiResponses({
            @ApiResponse(code = 404, message = "Photo not found by id", response = ErrorMessageREST.class)
    })
    public Response downloadOriginalImage(
            @ApiParam(value = "Photo number id", required = true)
            @PathParam("id") Long photoId) {
        OriginalImage originalImage = photoService.downloadOriginalImage(photoId);
        Response.ResponseBuilder builder = Response.ok(originalImage.getIn(), APPLICATION_OCTET_STREAM);
        builder.header("Content-Disposition", "attachment; filename=" + originalImage.getName());
        return builder.build();
    }
}
