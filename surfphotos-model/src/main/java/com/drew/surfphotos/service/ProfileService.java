package com.drew.surfphotos.service;

import com.drew.surfphotos.exception.ObjectNotFoundException;
import com.drew.surfphotos.model.AsyncOperation;
import com.drew.surfphotos.model.ImageResource;
import com.drew.surfphotos.model.domain.Profile;

import java.util.Optional;

/**
 * Представитель бизнес интерфейса, который определяет какие могут быть выполнены действия с предметной областью, через
 * SOAP, REST и Web(интерфейс) клиент
 */
public interface ProfileService {

    Profile findById(Long id) throws ObjectNotFoundException; //найти профиль по id, возвращает объект Profile

    Profile findByUid(String uid) throws ObjectNotFoundException; //если нужно найти пользователя по uid, возвращает объект Profile

    Optional<Profile> findByEmail(String email);//поиск по уникальному полю email, возвращает объект Optional

    void signUp(Profile profile, boolean uploadProfileAvatar);//регистрация пользователя, передаёт профиль и флаг(нужно ли загружать аватарку)

    void translitSocialProfile(Profile profile);//если в профиле существует рускоязычный контент, то произойдёт перевод, так как приложение для англоязычного контента

    void update(Profile profile);// обновления профиля, если хотим что-то изменить

    void uploadNewAvatar(Profile currentProfile, ImageResource imageResource, AsyncOperation<Profile> asyncOperation);//принимает объект профиля для которого загружается аватар, как с компа, так и с соц.сети, данная операция асинхронная
}
