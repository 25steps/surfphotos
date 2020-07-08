package com.drew.surfphotos.ejb.service.impl;

import com.drew.surfphotos.ejb.model.ProfileUidGenerator;
import com.drew.surfphotos.ejb.service.ProfileUidService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import static com.drew.surfphotos.ejb.model.ProfileUidGenerator.Category.PRIMARY;
import static com.drew.surfphotos.ejb.model.ProfileUidGenerator.Category.SECONDARY;

@ApplicationScoped
public class ProfileUidServiceManager {
    @Inject
    private Logger logger;

    @Inject
    @Any
    private Instance<ProfileUidService> profileUidServices;

    public List<String> getProfileUidCandidates(String englishFirstName, String englishLastName) {
        List<String> result = new ArrayList<>();
        addCandidates(new PrimaryProfileUidGenerator(),   result, englishFirstName, englishLastName);
        addCandidates(new SecondaryProfileUidGenerator(), result, englishFirstName, englishLastName);
        return Collections.unmodifiableList(result);
    }

    public String getDefaultUid(){
        return UUID.randomUUID().toString().replace("-", "");
    }

    private void addCandidates(AnnotationLiteral<ProfileUidGenerator> selector, List<String> result, String englishFirstName, String englishLastName) {
        Instance<ProfileUidService> services = profileUidServices.select(selector);
        for (ProfileUidService service : services) {
            result.addAll(service.generateProfileUidCandidates(englishFirstName, englishLastName));
        }
    }

    @PostConstruct
    private void postConstruct(){
        StringBuilder logStr = new StringBuilder("Detected the following ProfileUidService beans\n");
        for(ProfileUidService service : profileUidServices) {
            logStr.append(String.format("%s\n", service.getClass().getName()));
        }
        logger.info(logStr.toString());
    }

    private class PrimaryProfileUidGenerator extends AnnotationLiteral<ProfileUidGenerator> implements ProfileUidGenerator {

        public ProfileUidGenerator.Category category() {
            return PRIMARY;
        }
    }

    private class SecondaryProfileUidGenerator extends AnnotationLiteral<ProfileUidGenerator> implements ProfileUidGenerator {

        public ProfileUidGenerator.Category category() {
            return SECONDARY;
        }
    }
}
