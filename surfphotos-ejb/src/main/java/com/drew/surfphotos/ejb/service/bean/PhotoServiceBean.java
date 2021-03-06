package com.drew.surfphotos.ejb.service.bean;

import com.drew.surfphotos.ejb.repository.PhotoRepository;
import com.drew.surfphotos.ejb.repository.ProfileRepository;
import com.drew.surfphotos.ejb.service.ImageStorageService;
import com.drew.surfphotos.ejb.service.interceptor.AsyncOperationInterceptor;
import com.drew.surfphotos.exception.ObjectNotFoundException;
import com.drew.surfphotos.exception.ValidationException;
import com.drew.surfphotos.model.*;
import com.drew.surfphotos.model.domain.Photo;
import com.drew.surfphotos.model.domain.Profile;
import com.drew.surfphotos.service.PhotoService;

import javax.annotation.Resource;
import javax.ejb.*;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@Stateless
@LocalBean
@Local(PhotoService.class)
public class PhotoServiceBean implements PhotoService {
    @Inject
    private PhotoRepository photoRepository;

    @Inject
    private ProfileRepository profileRepository;

    @Inject
    private ImageStorageService imageStorageService;

    @EJB
    private ImageProcessorBean imageProcessorBean;

    @Resource
    private SessionContext sessionContext;

    @Override
    public List<Photo> findProfilePhotos(Long profileId, Pageable pageable) {
        return photoRepository.findProfilePhotosLatestFirst(profileId, pageable.getOffset(), pageable.getLimit());
    }

    @Override
    public List<Photo> findPopularPhotos(SortMode sortMode, Pageable pageable) {
        switch (sortMode) {
            case POPULAR_AUTHOR:
                return photoRepository.findAllOrderByAuthorRatingDesc(pageable.getOffset(), pageable.getLimit());
            case POPULAR_PHOTO:
                return photoRepository.findAllOrderByViewsDesc(pageable.getOffset(), pageable.getLimit());
            default:
                throw new ValidationException("Unsupported sort mode: " + sortMode);
        }
    }

    @Override
    public long countAllPhotos() {
        return photoRepository.countAll();
    }

    @Override
    public String viewLargePhoto(Long photoId) throws ObjectNotFoundException {
        Photo photo = getPhoto(photoId);
        photo.setViews(photo.getViews() + 1);
        photoRepository.update(photo);
        return photo.getLargeUrl();
    }

    public Photo getPhoto(Long photoId) throws ObjectNotFoundException {
        Optional<Photo> photo = photoRepository.findById(photoId);
        if (!photo.isPresent()) {
            throw new ObjectNotFoundException(format("Photo not found by id: %s", photoId));
        }
        return photo.get();
    }

    @Override
    public OriginalImage downloadOriginalImage(Long photoId) throws ObjectNotFoundException {
        Photo photo = getPhoto(photoId);
        photo.setDownloads(photo.getDownloads() + 1);
        photoRepository.update(photo);

        return imageStorageService.getOriginalImage(photo.getOriginalUrl());
    }

    @Override
    @Asynchronous
    @Interceptors(AsyncOperationInterceptor.class)
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void uploadNewPhoto(Profile currentProfile, ImageResource imageResource, AsyncOperation<Photo> asyncOperation) {
        try {
            Photo photo = uploadNewPhoto(currentProfile, imageResource);
            asyncOperation.onSuccess(photo);
        } catch (Throwable throwable) {
            sessionContext.setRollbackOnly();
            asyncOperation.onFailed(throwable);
        }
    }

    public Photo uploadNewPhoto(Profile currentProfile, ImageResource imageResource){
        Photo photo = imageProcessorBean.processPhoto(imageResource);
        photo.setProfile(currentProfile);
        photoRepository.create(photo);
        photoRepository.flush();
        currentProfile.setPhotoCount(photoRepository.countProfilePhotos(currentProfile.getId()));
        profileRepository.update(currentProfile);
        return photo;
    }
}
