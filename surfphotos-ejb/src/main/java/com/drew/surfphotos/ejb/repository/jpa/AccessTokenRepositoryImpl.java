package com.drew.surfphotos.ejb.repository.jpa;

import com.drew.surfphotos.ejb.repository.AccessTokenRepository;
import com.drew.surfphotos.model.domain.AccessToken;

import javax.enterprise.context.Dependent;
import java.util.Optional;


//@Dependent
public class AccessTokenRepositoryImpl extends AbstractJPARepository<AccessToken, String> implements AccessTokenRepository{

    @Override
    protected Class<AccessToken> getEntityClass() {
        return AccessToken.class;
    }

    @Override
    public Optional<AccessToken> findByToken(String token) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public boolean removeAccessToken(String token) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

}
