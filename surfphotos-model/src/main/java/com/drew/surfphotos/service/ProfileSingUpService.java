package com.drew.surfphotos.service;

import com.drew.surfphotos.exception.ObjectNotFoundException;
import com.drew.surfphotos.model.domain.Profile;

/**
 * Представитель бизнес интерфейса который используется для регистрации пользователя
 * В качестве хранения будем использовать оперативную память для неполного пользователя
 */
public interface ProfileSingUpService {
    void createSignUpProfile(Profile profile);//создания нового объекта профиля

    Profile getCurrentProfile() throws ObjectNotFoundException;//получать и заполнять существующий

    void completeSignUp();//как только все поля формы пользователя будут заполнены, то будет профиле перенесён в базу данных

    void cancel();//отмена всх изменений
}
