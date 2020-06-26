package com.drew.surfphotos.generation.component;

import com.drew.surfphotos.ejb.repository.PhotoRepository;
import com.drew.surfphotos.model.domain.Photo;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Random;

/**
 * Данный компонент предоставляет возможность обновить фото
 */
@ApplicationScoped
public class UpdatePhotoService {
    @Inject
    private PhotoRepository photoRepository;

    private Random random = new Random();

    @Transactional
    public void updatePhotos(List<Photo> photos) {
        for(Photo photo : photos) {
            photo.setDownloads(random.nextInt(100));
            photo.setViews(random.nextInt(1000) * 5 + 100);
            photoRepository.update(photo);
        }
    }
}
