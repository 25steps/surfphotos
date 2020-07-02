package com.drew.surfphotos.ejb.service.bean;

import com.drew.surfphotos.common.annotation.cdi.Property;
import com.drew.surfphotos.common.config.ImageCategory;
import com.drew.surfphotos.ejb.repository.ProfileRepository;
import com.drew.surfphotos.ejb.service.ImageStorageService;
import com.drew.surfphotos.ejb.service.interceptor.AsyncOperationInterceptor;
import com.drew.surfphotos.exception.ObjectNotFoundException;
import com.drew.surfphotos.model.AsyncOperation;
import com.drew.surfphotos.model.ImageResource;
import com.drew.surfphotos.model.domain.Profile;
import com.drew.surfphotos.service.ProfileService;

import javax.ejb.*;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.Optional;

@Stateless
@LocalBean
@Local(ProfileService.class)
public class ProfileServiceBean implements ProfileService {
    @Inject
    @Property("surfphotos.profile.avatar.placeholder.url")
    private String avatarPlaceHolderUrl;

    @Inject
    private ProfileRepository profileRepository;

    @EJB
    private ImageProcessorBean imageProcessorBean;

    @Inject
    private ImageStorageService imageStorageService;

    @Override
    public Profile findById(Long id) throws ObjectNotFoundException {
        Optional<Profile> profile = profileRepository.findById(id);
        if (!profile.isPresent()) {
            throw new ObjectNotFoundException(String.format("Profile not found by id: %s", id));
        }
        return profile.get();
    }

    @Override
    public Profile findByUid(String uid) throws ObjectNotFoundException {
        Optional<Profile> profile = profileRepository.findByUid(uid);
        if (!profile.isPresent()) {
            throw new ObjectNotFoundException(String.format("Profile not found by uid: %s", uid));
        }
        return profile.get();
    }

    @Override
    public Optional<Profile> findByEmail(String email) {
        return profileRepository.findByEmail(email);
    }

    @Override
    public void signUp(Profile profile, boolean uploadProfileAvatar) {
        profileRepository.create(profile);
    }

    @Override
    public void translitSocialProfile(Profile profile) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public void update(Profile profile) {
        profileRepository.update(profile);
    }

    @Override
    @Asynchronous
    @Interceptors(AsyncOperationInterceptor.class)
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void uploadNewAvatar(Profile currentProfile, ImageResource imageResource, AsyncOperation<Profile> asyncOperation) {
        try {
            uploadNewAvatar(currentProfile, imageResource);
            asyncOperation.onSuccess(currentProfile);
        } catch (Throwable throwable) {
            setAvatarPlaceHolder(currentProfile);
            asyncOperation.onFailed(throwable);
        }
    }

    public void uploadNewAvatar(Profile currentProfile, ImageResource imageResource) {
        String avatarUrl = imageProcessorBean.processProfileAvatar(imageResource);
        if (ImageCategory.isImageCategoryUrl(currentProfile.getAvatarUrl())) {
            imageStorageService.deletePublicImage(currentProfile.getAvatarUrl());
        }
        currentProfile.setAvatarUrl(avatarUrl);
        profileRepository.update(currentProfile);
    }

    public void setAvatarPlaceHolder(Long profileId) {
        setAvatarPlaceHolder(profileRepository.findById(profileId).get());
    }

    public void setAvatarPlaceHolder(Profile currentProfile) {
        if (currentProfile.getAvatarUrl() == null) {
            currentProfile.setAvatarUrl(avatarPlaceHolderUrl);
            profileRepository.update(currentProfile);
        }
    }
}
