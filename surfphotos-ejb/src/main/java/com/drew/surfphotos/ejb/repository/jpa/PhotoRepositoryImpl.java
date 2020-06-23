package com.drew.surfphotos.ejb.repository.jpa;

import com.drew.surfphotos.ejb.repository.PhotoRepository;
import com.drew.surfphotos.model.domain.Photo;
import com.drew.surfphotos.ejb.repository.jpa.StaticJPAQueryInitializer.JPAQuery;

import javax.enterprise.context.Dependent;
import java.util.List;

@Dependent
public class PhotoRepositoryImpl extends AbstractJPARepository<Photo, Long> implements PhotoRepository {
    @Override
    protected Class<Photo> getEntityClass() {
        return Photo.class;
    }

    @Override
    @JPAQuery("SELECT ph FROM Photo ph WHERE ph.profile.id=:profileId ORDER BY ph.id DESC")
    public List<Photo> findProfilePhotosLatestFirst(Long profileId, int offset, int limit) {
        return em.createNamedQuery("Photo.findProfilePhotosLatestFirst")
                .setParameter("profileId", profileId)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    @JPAQuery("SELECT COUNT(ph) FROM Photo ph WHERE ph.profile.id=:profileId")
    public int countProfilePhotos(Long profileId) {
        Object count = em
                .createNamedQuery("Photo.countProfilePhotos")
                .setParameter("profileId", profileId)
                .getSingleResult();
        return ((Number)count).intValue();
    }

    @Override
    @JPAQuery("SELECT e FROM Photo e JOIN FETCH e.profile ORDER BY e.views DESC")
    public List<Photo> findAllOrderByViewsDesc(int offset, int limit) {
        return em.createNamedQuery("Photo.findAllOrderByViewsDesc")
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    @JPAQuery("SELECT e FROM Photo e JOIN FETCH e.profile p ORDER BY p.rating DESC, e.views DESC")
    public List<Photo> findAllOrderByAuthorRatingDesc(int offset, int limit) {
        return em.createNamedQuery("Photo.findAllOrderByAuthorRatingDesc")
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    @JPAQuery("SELECT COUNT(ph) FROM Photo ph")
    public long countAll() {
        Object count = em
                .createNamedQuery("Photo.countAll")
                .getSingleResult();
        return ((Number)count).intValue();
    }
}
