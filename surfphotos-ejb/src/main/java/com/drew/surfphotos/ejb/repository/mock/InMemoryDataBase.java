package com.drew.surfphotos.ejb.repository.mock;

import com.drew.surfphotos.model.domain.Photo;
import com.drew.surfphotos.model.domain.Profile;

import java.util.*;

public final class InMemoryDataBase {
    public static final Profile PROFILE;

    public static final List<Photo> PHOTOS;

    static {
        PROFILE = createProfile();
        PHOTOS = createPhotos(PROFILE);
    }

    private static Profile createProfile() {
        Profile profile = new Profile();
        profile.setId(1L);
        profile.setUid("john-wood");
        profile.setCreated(new Date());
        profile.setFirstName("John");
        profile.setLastName("Wood");
        profile.setJobTitle("Photographer");
        profile.setLocation("Los Angeles, California");
        profile.setAvatarUrl("https://raw.githubusercontent.com/25steps/surfphoto-test/master/avatar.jpg");
        return profile;
    }

    private static List<Photo> createPhotos(Profile profile) {
        Random random = new Random();
        List<Photo> photos = new ArrayList<>();
        for (int i = 1; i <= 15; i++) {
            Photo photo = new Photo();
            photo.setProfile(profile);
            profile.setPhotoCount(profile.getPhotoCount() + 1);
            String imageUrl = String.format("https://raw.githubusercontent.com/25steps/surfphoto-test/master/%s.jpg", i % 6 + 1);
            photo.setSmallUrl(imageUrl);
            photo.setLargeUrl("https://raw.githubusercontent.com/25steps/surfphoto-test/master/unsplash.jpg");
            photo.setOriginalUrl(imageUrl);
            photo.setViews(random.nextInt(100) * 10 + 1);
            photo.setDownloads(random.nextInt(20) * 10 + 1);
            photo.setCreated(new Date());
            photos.add(photo);
        }
        return Collections.unmodifiableList(photos);
    }

    private InMemoryDataBase() {
    }
}
