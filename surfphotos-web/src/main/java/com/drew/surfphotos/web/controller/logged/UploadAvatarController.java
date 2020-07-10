package com.drew.surfphotos.web.controller.logged;

import com.drew.surfphotos.model.AsyncOperation;
import com.drew.surfphotos.model.domain.Profile;
import com.drew.surfphotos.service.ProfileService;
import com.drew.surfphotos.web.model.PartImageResource;

import javax.inject.Inject;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;

import static com.drew.surfphotos.common.config.Constants.MAX_UPLOADED_PHOTO_SIZE_IN_BYTES;

@WebServlet(urlPatterns = "/upload-avatar", asyncSupported = true, loadOnStartup = 1)
@MultipartConfig(maxFileSize = MAX_UPLOADED_PHOTO_SIZE_IN_BYTES)
public class UploadAvatarController extends AbstractUploadController<Profile> {

    @Inject
    private ProfileService profileService;

    @Override
    protected void uploadImage(Profile profile, PartImageResource partImageResource, AsyncOperation<Profile> asyncOperation) {
        profileService.uploadNewAvatar(profile, partImageResource, asyncOperation);
    }

    @Override
    protected Map<String, String> getResultMap(Profile result, HttpServletRequest request) {
        return Collections.singletonMap("thumbnailUrl", result.getAvatarUrl());
    }
}

