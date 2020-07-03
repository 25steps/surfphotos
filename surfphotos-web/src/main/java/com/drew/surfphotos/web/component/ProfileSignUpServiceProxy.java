package com.drew.surfphotos.web.component;


import com.drew.surfphotos.exception.InvalidWorkFlowException;
import com.drew.surfphotos.model.domain.Profile;
import com.drew.surfphotos.service.ProfileSignUpService;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

@SessionScoped
public class ProfileSignUpServiceProxy implements ProfileSignUpService, Serializable {

    @EJB
    private ProfileSignUpService profileSignUpService;

    @Inject
    private transient Logger logger;

    @Override
    public void createSignUpProfile(Profile profile) {
        validate();
        profileSignUpService.createSignUpProfile(profile);
    }

    @Override
    public Profile getCurrentProfile() {
        validate();
        return profileSignUpService.getCurrentProfile();
    }

    @Override
    public void completeSignUp() {
        validate();
        profileSignUpService.completeSignUp();
        profileSignUpService = null;
    }

    private void validate() {
        if (profileSignUpService == null) {
            throw new InvalidWorkFlowException("Can't use ProfileSignUpService after completeSignUp() or cancel() invokation");
        }
    }

    @PostConstruct
    private void postConstruct() {
        logger.log(Level.FINE, "Created {0} instance: {1}", new Object[]{getClass().getSimpleName(), System.identityHashCode(this)});
    }

    @PreDestroy
    private void preDestroy() {
        logger.log(Level.FINE, "Destroyed {0} instance: {1}", new Object[]{getClass().getSimpleName(), System.identityHashCode(this)});
        // Remove stateful bean if current session destroyed
        if (profileSignUpService != null) {
            profileSignUpService.cancel();
            profileSignUpService = null;
        }
    }

    @Override
    public void cancel() {
        profileSignUpService.cancel();
        profileSignUpService = null;
    }
}
