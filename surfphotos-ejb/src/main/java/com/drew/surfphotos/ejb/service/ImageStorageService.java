package com.drew.surfphotos.ejb.service;

import com.drew.surfphotos.common.config.ImageCategory;
import com.drew.surfphotos.model.OriginalImage;

import java.nio.file.Path;

public interface ImageStorageService {
    String saveProtectedImage(Path path);

    String savePublicImage(ImageCategory imageCategory, Path path);

    void deletePublicImage(String url);

    OriginalImage getOriginalImage(String originalUrl);
}
