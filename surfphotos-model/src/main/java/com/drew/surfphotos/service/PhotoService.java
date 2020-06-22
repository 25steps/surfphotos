package com.drew.surfphotos.service;

import com.drew.surfphotos.exception.ObjectNotFoundException;
import com.drew.surfphotos.model.*;
import com.drew.surfphotos.model.domain.Photo;
import com.drew.surfphotos.model.domain.Profile;

import java.util.List;

/**
 * Представитель бизнес интерфейса для работы с фотографиями
 */
public interface PhotoService {
    List<Photo> findProfilePhotos(Long profileId, Pageable pageable);//найти фотографии профиля

    List<Photo> findPopularPhotos(SortMode sortMode, Pageable pageable);//отображает список фотографий для хоумстранички с методом сортировки

    long countAllPhotos();//для постраничного отображения всех фотографий

    String viewLargePhoto(Long photoId) throws ObjectNotFoundException;//метод задействуется, когда пользователь открывает фотографию, так же он увеличивает кол-во просмотров

    OriginalImage downloadOriginalImage(Long photoId) throws ObjectNotFoundException;//метод задействуется, когда пользователь выгружает фотографию, так же он увеличивает кол-во загрузок

    void uploadNewPhoto(Profile currentProfile, ImageResource imageResource, AsyncOperation<Photo> asyncOperation);//используется для загрузки новой порции фотографий
}
