package com.drew.surfphotos.ejb.service.impl;

import com.drew.surfphotos.common.annotation.cdi.Property;
import com.drew.surfphotos.common.annotation.qualifier.GooglePlus;
import com.drew.surfphotos.exception.RetrieveSocialDataFailedException;
import com.drew.surfphotos.model.domain.Profile;
import com.drew.surfphotos.service.SocialService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@GooglePlus
@ApplicationScoped
public class GooglePlusSocialService implements SocialService {
//  Не подтягиваются значения из envprop..
//    @Inject
//    @Property("surfphotos.social.google-plus.client-id")
    private String clientId = System.getenv("googlePlusClientId");

    private List<String> issuers;

    @Inject
    public void setIssuers(@Property("surfphotos.social.google-plus.issuers") String issuers) {
        this.issuers = Collections.unmodifiableList(Arrays.asList(issuers.split(",")));
    }

    @Override
    public String getClientId() {
        return clientId;
    }

    @Override
    public Profile fetchProfile(String code) throws RetrieveSocialDataFailedException {
        try {
            return createProfile(fetch(code));
        } catch (GeneralSecurityException | IOException | RuntimeException e) {
            if (e instanceof RetrieveSocialDataFailedException) {
                throw (RetrieveSocialDataFailedException) e;
            } else {
                throw new RetrieveSocialDataFailedException("Can't fetch user from google plus: " + e.getMessage(), e);
            }
        }
    }

    private GoogleIdToken.Payload fetch(String code) throws GeneralSecurityException, IOException {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance())
                .setAudience(Collections.singleton(clientId))
                .setIssuers(issuers).build();
        Optional<GoogleIdToken> idToken = Optional.ofNullable(verifier.verify(code));
        if (idToken.isPresent()) {
            return idToken.get().getPayload();
        } else {
            throw new RetrieveSocialDataFailedException("Can't get account by authToken: " + code);
        }
    }

    private Profile createProfile(GoogleIdToken.Payload user) {
        Profile profile = new Profile();
        profile.setEmail(user.getEmail());
        profile.setFirstName((String) user.get("given_name"));
        profile.setLastName((String) user.get("family_name"));
        profile.setAvatarUrl((String) user.get("picture"));
        return profile;
    }
}
