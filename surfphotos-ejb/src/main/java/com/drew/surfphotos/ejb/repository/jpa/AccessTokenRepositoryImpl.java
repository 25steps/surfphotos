package com.drew.surfphotos.ejb.repository.jpa;

import com.drew.surfphotos.ejb.repository.AccessTokenRepository;
import com.drew.surfphotos.model.domain.AccessToken;
import com.drew.surfphotos.ejb.repository.jpa.StaticJPAQueryInitializer.JPAQuery;

import javax.enterprise.context.Dependent;
import javax.persistence.NoResultException;
import java.util.Optional;


@Dependent
public class AccessTokenRepositoryImpl extends AbstractJPARepository<AccessToken, String> implements AccessTokenRepository{

    @Override
    protected Class<AccessToken> getEntityClass() {
        return AccessToken.class;
    }

    @Override
    @JPAQuery("SELECT at FROM AccessToken at JOIN FETCH at.profile WHERE at.token=:token")
    public Optional<AccessToken> findByToken(String token) {
        try {
            AccessToken accessToken = (AccessToken) em
                    .createNamedQuery("AccessToken.findByToken")
                    .setParameter("token", token)
                    .getSingleResult();
            return Optional.of(accessToken);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    @JPAQuery("DELETE FROM AccessToken at WHERE at.token=:token")
    public boolean removeAccessToken(String token) {
        int result = em
                .createNamedQuery("AccessToken.removeAccessToken")
                .setParameter("token", token)
                .executeUpdate();
        return result == 1;
    }

}
