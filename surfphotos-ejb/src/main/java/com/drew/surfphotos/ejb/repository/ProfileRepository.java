package com.drew.surfphotos.ejb.repository;

import com.drew.surfphotos.model.domain.Profile;

import java.util.List;
import java.util.Optional;

public interface ProfileRepository extends EntityRepository<Profile, Long> {

    Optional<Profile> findByUid(String uid);

    Optional<Profile> findByEmail(String email);

    void updateRating();//вызывает хранимую процидуру, чтобы обновить рейтинг

    List<String> findUids(List<String> uids);
}
