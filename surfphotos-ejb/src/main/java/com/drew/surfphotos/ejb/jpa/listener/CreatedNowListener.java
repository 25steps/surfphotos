package com.drew.surfphotos.ejb.jpa.listener;

import com.drew.surfphotos.model.domain.AbstractDomain;

import javax.persistence.PrePersist;
import java.util.Date;

public class CreatedNowListener {
    @PrePersist
    public void setNow(AbstractDomain model) {
        model.setCreated(new Date(System.currentTimeMillis()-1));
    }
}
