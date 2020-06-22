package com.drew.surfphotos.ejb.repository.jpa;

import com.drew.surfphotos.ejb.repository.ProfileRepository;
import com.drew.surfphotos.model.domain.Profile;

import javax.enterprise.context.Dependent;
import java.util.List;
import java.util.Optional;

//@Dependent //какая то странная ошибка, без них работает
public class ProfileRepositoryImpl extends AbstractJPARepository<Profile, Long> implements ProfileRepository {
    @Override
    protected Class<Profile> getEntityClass() {
        return Profile.class;
    }

    @Override
    public Optional<Profile> findByUid(String uid) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public Optional<Profile> findByEmail(String email) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public void updateRating() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public List<String> findUids(List<String> uids) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}
