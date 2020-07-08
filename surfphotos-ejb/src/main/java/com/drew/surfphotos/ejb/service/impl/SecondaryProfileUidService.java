package com.drew.surfphotos.ejb.service.impl;

import com.drew.surfphotos.ejb.model.ProfileUidGenerator;
import com.drew.surfphotos.ejb.service.ProfileUidService;

import javax.enterprise.context.ApplicationScoped;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.drew.surfphotos.ejb.model.ProfileUidGenerator.Category.SECONDARY;

@ApplicationScoped
@ProfileUidGenerator(category = SECONDARY)
public class SecondaryProfileUidService implements ProfileUidService {
    @Override
    public List<String> generateProfileUidCandidates(String englishFirstName, String englishLastName) {
        return Collections.unmodifiableList(Arrays.asList(
                String.format("%s-%s", englishFirstName.charAt(0), englishLastName).toLowerCase(),
                String.format("%s.%s", englishFirstName.charAt(0), englishLastName).toLowerCase(),
                String.format("%s%s", englishFirstName.charAt(0), englishLastName).toLowerCase(),
                englishLastName.toLowerCase(),
                englishFirstName.toLowerCase()
        ));
    }
}
