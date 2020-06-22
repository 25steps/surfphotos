package com.drew.surfphotos.ejb.repository.jpa;

import com.drew.surfphotos.ejb.repository.PhotoRepository;
import com.drew.surfphotos.model.domain.Photo;

import javax.enterprise.context.Dependent;
import java.util.List;

//@Dependent
public class PhotoRepositoryImpl extends AbstractJPARepository<Photo, Long> implements PhotoRepository {
    @Override
    protected Class<Photo> getEntityClass() {
        return Photo.class;
    }

    @Override
    public List<Photo> findProfilePhotosLatestFirst(Long profileId, int offset, int limit) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public int countProfilePhotos(Long profileId) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public List<Photo> findAllOrderByViewsDesc(int offset, int limit) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public List<Photo> findAllOrderByAuthorRatingDesc(int offset, int limit) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public long countAll() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}
