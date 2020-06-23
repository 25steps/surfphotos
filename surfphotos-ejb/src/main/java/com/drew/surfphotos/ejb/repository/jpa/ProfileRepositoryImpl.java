package com.drew.surfphotos.ejb.repository.jpa;

import com.drew.surfphotos.ejb.repository.ProfileRepository;
import com.drew.surfphotos.model.domain.Profile;
import com.drew.surfphotos.ejb.repository.jpa.StaticJPAQueryInitializer.JPAQuery;

import javax.enterprise.context.Dependent;
import javax.persistence.NoResultException;
import javax.persistence.StoredProcedureQuery;
import java.util.List;
import java.util.Optional;

@Dependent
public class ProfileRepositoryImpl extends AbstractJPARepository<Profile, Long> implements ProfileRepository {
    @Override
    protected Class<Profile> getEntityClass() {
        return Profile.class;
    }

    @Override
    @JPAQuery("SELECT p FROM Profile p WHERE p.uid=:uid")
    public Optional<Profile> findByUid(String uid) {
        try {
            Profile profile = (Profile) em.createNamedQuery("Profile.findByUid")
                    .setParameter("uid", uid)
                    .getSingleResult();
            return Optional.of(profile);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    @JPAQuery("SELECT p FROM Profile p WHERE p.email=:email")
    public Optional<Profile> findByEmail(String email) {
        try {
            Profile profile = (Profile) em
                    .createNamedQuery("Profile.findByEmail")
                    .setParameter("email", email)
                    .getSingleResult();
            return Optional.of(profile);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public void updateRating() {
        StoredProcedureQuery query = em
                .createStoredProcedureQuery("update_rating");
        query.execute();
    }

    @Override
    @JPAQuery("SELECT p.uid FROM Profile p WHERE p.uid IN :uids")
    public List<String> findUids(List<String> uids) {
        return em
                .createNamedQuery("Profile.findUids")
                .setParameter("uids", uids)
                .getResultList();
    }
}
