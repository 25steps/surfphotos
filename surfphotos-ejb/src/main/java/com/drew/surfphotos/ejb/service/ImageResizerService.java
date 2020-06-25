package com.drew.surfphotos.ejb.service;

import com.drew.surfphotos.common.config.ImageCategory;

import java.nio.file.Path;

public interface ImageResizerService {
    void resize(Path sourcePath, Path destinationPath, ImageCategory imageCategory);
}
