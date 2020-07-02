package com.drew.surfphotos.generator.alternative;

import com.drew.surfphotos.ejb.repository.jpa.PhotoRepositoryImpl;

import javax.enterprise.context.Dependent;

@Dependent
@TestDataGeneratorEnvironment
public class PhotoRepositoryImplAlternative extends PhotoRepositoryImpl {
    @Override
    public int countProfilePhotos(Long profileId) {
        Object count = em.createQuery("SELECT COUNT(ph) FROM Photo ph WHERE ph.profile.id=:profileId")
                .setParameter("profileId", profileId)
                .getSingleResult();
        return ((Number)count).intValue();
    }
}
