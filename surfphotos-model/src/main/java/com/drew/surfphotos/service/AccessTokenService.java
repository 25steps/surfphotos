package com.drew.surfphotos.service;

import com.drew.surfphotos.exception.AccessForbiddenException;
import com.drew.surfphotos.exception.InvalidAccessTokenException;
import com.drew.surfphotos.model.domain.AccessToken;
import com.drew.surfphotos.model.domain.Profile;

/**
 * Представитель бизнес интерфейса который управляет аксесстокенами
 */
public interface AccessTokenService {
    AccessToken generateAccessToken(Profile profile);//генерирует новый токен в базе данных для текущего профиля

    Profile findProfile(String token, Long profileId) throws AccessForbiddenException, InvalidAccessTokenException;//принимает токен профиля и id, чтобы найти по нему информацию

    void invalidateAccessToken(String token) throws InvalidAccessTokenException;//удаляет токен с базы данных
}
