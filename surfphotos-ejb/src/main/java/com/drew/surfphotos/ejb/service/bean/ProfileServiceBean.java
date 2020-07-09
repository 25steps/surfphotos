package com.drew.surfphotos.ejb.service.bean;

import com.drew.surfphotos.common.annotation.cdi.Property;
import com.drew.surfphotos.common.config.ImageCategory;
import com.drew.surfphotos.ejb.model.URLImageResource;
import com.drew.surfphotos.ejb.repository.ProfileRepository;
import com.drew.surfphotos.ejb.service.ImageStorageService;
import com.drew.surfphotos.ejb.service.TranslitConverter;
import com.drew.surfphotos.ejb.service.impl.ProfileUidServiceManager;
import com.drew.surfphotos.ejb.service.interceptor.AsyncOperationInterceptor;
import com.drew.surfphotos.exception.ObjectNotFoundException;
import com.drew.surfphotos.model.AsyncOperation;
import com.drew.surfphotos.model.ImageResource;
import com.drew.surfphotos.model.domain.Profile;
import com.drew.surfphotos.service.ProfileService;

import javax.ejb.*;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import java.util.List;
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

    @Inject
    private ProfileUidServiceManager profileUidServiceManager;

    @Inject
    private TranslitConverter translitConverter;

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
        if(profile.getUid() == null) {
            setProfileUid(profile);
        }
        profileRepository.create(profile);
        if (uploadProfileAvatar && profile.getAvatarUrl() != null) {
            uploadNewAvatar(profile, new URLImageResource(profile.getAvatarUrl()));
        }
    }

    private void setProfileUid(Profile profile) {
        List<String> uids = profileUidServiceManager.getProfileUidCandidates(profile.getFirstName(), profile.getLastName());
        List<String> existUids = profileRepository.findUids(uids);
        for (String uid : uids) {
            if (!existUids.contains(uid)) {
                profile.setUid(uid);
                return;
            }
        }

        profile.setUid(profileUidServiceManager.getDefaultUid());
    }

    @Override
    public void translitSocialProfile(Profile profile) {
        profile.setFirstName(profile.getFirstName() != null ? translitConverter.translit(profile.getFirstName()) : null);
        profile.setLastName(profile.getLastName() != null ? translitConverter.translit(profile.getLastName()) : null);
        profile.setJobTitle(profile.getJobTitle() != null ? translitConverter.translit(profile.getJobTitle()) : null);
        profile.setLocation(profile.getLocation() != null ? translitConverter.translit(profile.getLocation()) : null);
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
