package com.drew.surfphotos.web.controller.logged;

import com.drew.surfphotos.model.AsyncOperation;
import com.drew.surfphotos.model.Pageable;
import com.drew.surfphotos.model.domain.Photo;
import com.drew.surfphotos.model.domain.Profile;
import com.drew.surfphotos.service.PhotoService;
import com.drew.surfphotos.web.model.PartImageResource;
import com.drew.surfphotos.web.security.SecurityUtils;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.drew.surfphotos.common.config.Constants.MAX_UPLOADED_PHOTO_SIZE_IN_BYTES;
import static com.drew.surfphotos.web.Constants.PHOTO_LIMIT;
import static com.drew.surfphotos.web.util.RoutingUtils.forwardToPage;

@WebServlet(urlPatterns = "/upload-photos", asyncSupported = true, loadOnStartup = 1)
@MultipartConfig(maxFileSize = MAX_UPLOADED_PHOTO_SIZE_IN_BYTES)
public class UploadPhotosController extends AbstractUploadController<Photo> {

    @Inject
    private PhotoService photoService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Profile profile = SecurityUtils.getCurrentProfile();
        List<Photo> photos = photoService.findProfilePhotos(profile.getId(), new Pageable(1, PHOTO_LIMIT - 1));
        req.setAttribute("profile", profile);
        req.setAttribute("photos", photos);
        req.setAttribute("profilePhotos", Boolean.TRUE);
        forwardToPage("upload-photos", req, resp);
    }

    @Override
    protected void uploadImage(Profile profile, PartImageResource partImageResource, AsyncOperation<Photo> asyncOperation) {
        photoService.uploadNewPhoto(profile, partImageResource, asyncOperation);
    }

    @Override
    protected Map<String, String> getResultMap(Photo photo, HttpServletRequest request) {
        Map<String, String> map = new HashMap<>();
        map.put("smallUrl", photo.getSmallUrl());
        map.put("largeUrl", String.format("/preview/%s.jpg", photo.getId()));
        map.put("originalUrl", String.format("/download/%s.jpg", photo.getId()));
        map.put("views", String.valueOf(photo.getViews()));
        map.put("downloads", String.valueOf(photo.getDownloads()));
        map.put("created", DateFormat.getDateInstance(DateFormat.SHORT, request.getLocale()).format(photo.getCreated()));
        return map;
    }
}
