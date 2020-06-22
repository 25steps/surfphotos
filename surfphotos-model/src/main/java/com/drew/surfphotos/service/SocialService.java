package com.drew.surfphotos.service;

import com.drew.surfphotos.exception.RetrieveSocialDataFailedException;
import com.drew.surfphotos.model.domain.Profile;

/**
 * Представитель бизнес интерфейса который взаимодействует с соц. сервисами(facebook, google+)
 */
public interface SocialService {
    Profile fetchProfile(String code) throws RetrieveSocialDataFailedException;//получает объект профиля по указанному коду

    String getClientId();//возвращает id клиента

    default String getAuthorizeUrl() {
        throw new UnsupportedOperationException();
    }//возвращает url по которому будут авторизовываться(нужен для facebook)
}
