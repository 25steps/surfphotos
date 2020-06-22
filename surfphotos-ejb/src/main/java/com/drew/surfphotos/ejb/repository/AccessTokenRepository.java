package com.drew.surfphotos.ejb.repository;

import com.drew.surfphotos.model.domain.AccessToken;

import java.util.Optional;

public interface AccessTokenRepository extends EntityRepository<AccessToken, String> {
    Optional<AccessToken> findByToken(String token);

    boolean removeAccessToken(String token);
}
