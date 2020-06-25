package com.drew.surfphotos.ejb.service.bean;

import com.drew.surfphotos.common.config.ImageCategory;
import com.drew.surfphotos.common.model.TempImageResource;
import com.drew.surfphotos.ejb.service.ImageResizerService;
import com.drew.surfphotos.ejb.service.ImageStorageService;
import com.drew.surfphotos.ejb.service.interceptor.ImageResourceInterceptor;
import com.drew.surfphotos.model.ImageResource;
import com.drew.surfphotos.model.domain.Photo;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;

import static com.drew.surfphotos.common.config.ImageCategory.*;

@Stateless
@LocalBean
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class ImageProcessorBean {
    @Inject
    private ImageResizerService imageResizerService;

    @Inject
    private ImageStorageService imageStorageService;

    @Interceptors({ImageResourceInterceptor.class})
    public String processProfileAvatar(ImageResource imageResource) {
        return createResizedImage(imageResource, PROFILE_AVATAR);
    }

    @Interceptors({ImageResourceInterceptor.class})
    public Photo processPhoto(ImageResource imageResource) {
        Photo photo = new Photo();
        photo.setLargeUrl(createResizedImage(imageResource, LARGE_PHOTO));
        photo.setSmallUrl(createResizedImage(imageResource, SMALL_PHOTO));
        photo.setOriginalUrl(imageStorageService.saveProtectedImage(imageResource.getTempPath()));
        return photo;
    }

    private String createResizedImage(ImageResource imageResource, ImageCategory imageCategory) {
        try (TempImageResource tempPath = new TempImageResource()) {
            imageResizerService.resize(imageResource.getTempPath(), tempPath.getTempPath(), imageCategory);
            return imageStorageService.savePublicImage(imageCategory, tempPath.getTempPath());
        }
    }
}
