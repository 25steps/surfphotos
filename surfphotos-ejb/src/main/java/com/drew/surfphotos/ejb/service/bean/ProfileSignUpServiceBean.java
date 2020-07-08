package com.drew.surfphotos.ejb.service.bean;


import com.drew.surfphotos.exception.ObjectNotFoundException;
import com.drew.surfphotos.model.domain.Profile;
import com.drew.surfphotos.service.ProfileService;
import com.drew.surfphotos.service.ProfileSignUpService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.*;
import javax.inject.Inject;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.concurrent.TimeUnit.MINUTES;

@Stateful
@StatefulTimeout(value = 30, unit = MINUTES)
public class ProfileSignUpServiceBean implements ProfileSignUpService, Serializable{

    @Inject
    private transient Logger logger;

    @Inject
    private transient ProfileService profileService;

    private Profile profile;

    @Override
    public void createSignUpProfile(Profile profile) {
        this.profile = profile;
        profileService.translitSocialProfile(profile);
    }

    @Override
    public Profile getCurrentProfile() throws ObjectNotFoundException {
        if (profile == null) {
            throw new ObjectNotFoundException("Profile not found. Please create profile before use");
        }
        return profile;
    }

    @Override
    @Remove
    public void completeSignUp() {
        profileService.signUp(profile, false);
    }

    @Override
    @Remove
    public void cancel() {
        profile = null;
    }

    @PostConstruct
    private void postConstruct() {
        logger.log(Level.FINE, "Created {0} instance: {1}", new Object[]{getClass().getSimpleName(), System.identityHashCode(this)});
    }

    @PreDestroy
    private void preDestroy() {
        logger.log(Level.FINE, "Destroyed {0} instance: {1}", new Object[]{getClass().getSimpleName(), System.identityHashCode(this)});
    }

    @PostActivate
    private void postActivate() {
        logger.log(Level.FINE, "Activated {0} instance: {1}", new Object[]{getClass().getSimpleName(), System.identityHashCode(this)});
    }

    @PrePassivate
    private void prePassivate() {
        logger.log(Level.FINE, "Passivated {0} instance: {1}", new Object[]{getClass().getSimpleName(), System.identityHashCode(this)});
    }
}