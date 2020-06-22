package com.drew.surfphotos.ejb.jpa.listener;

import com.drew.surfphotos.exception.InvalidWorkFlowException;
import com.drew.surfphotos.model.domain.AccessToken;

import javax.inject.Inject;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AccessTokenLifeCycleManager {
    @Inject
    private Logger logger;

    @PrePersist
    public void setToken(AccessToken model) {
        model.setToken(UUID.randomUUID().toString().replace("-", ""));
        logger.log(Level.FINE, "Generate new uid token {0} for entity {1}", new Object[]{model.getToken(), model.getClass()});
    }

    @PreUpdate
    public void rejectUpdate(AccessToken model) {
        throw new InvalidWorkFlowException("AccessToken is not updatedable");
    }
}
