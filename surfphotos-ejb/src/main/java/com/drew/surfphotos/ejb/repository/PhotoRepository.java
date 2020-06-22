package com.drew.surfphotos.ejb.repository;

import com.drew.surfphotos.model.domain.Photo;

import java.util.List;

public interface PhotoRepository extends EntityRepository<Photo, Long> {

    List<Photo> findProfilePhotosLatestFirst(Long profileId, int offset, int limit);

    int countProfilePhotos(Long profileId);

    List<Photo> findAllOrderByViewsDesc(int offset, int limit);

    List<Photo> findAllOrderByAuthorRatingDesc(int offset, int limit);

    long countAll();
}
