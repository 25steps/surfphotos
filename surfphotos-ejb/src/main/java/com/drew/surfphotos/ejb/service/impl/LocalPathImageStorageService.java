package com.drew.surfphotos.ejb.service.impl;

import com.drew.surfphotos.common.annotation.cdi.Property;
import com.drew.surfphotos.common.config.ImageCategory;
import com.drew.surfphotos.ejb.service.FileNameGeneratorService;
import com.drew.surfphotos.ejb.service.ImageStorageService;
import com.drew.surfphotos.exception.ApplicationException;
import com.drew.surfphotos.model.OriginalImage;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.String.format;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@ApplicationScoped
public class LocalPathImageStorageService implements ImageStorageService {
    @Inject
    private Logger logger;

    @Inject
    @Property("surfphotos.storage.root.dir")
    private String storageRoot;

    @Inject
    @Property("surfphotos.media.absolute.root")
    private String mediaRoot;

    @Inject
    private FileNameGeneratorService fileNameGeneratorService;

    @Override
    public String saveProtectedImage(Path path) {
        String fileName = fileNameGeneratorService.generateUniqueFileName();
        Path destinationPath = Paths.get(storageRoot, fileName);
        saveImage(path, destinationPath);
        return fileName;
    }

    @Override
    public String savePublicImage(ImageCategory imageCategory, Path path) {
        String fileName = fileNameGeneratorService.generateUniqueFileName();
        Path destinationPath = Paths.get(mediaRoot, imageCategory.getRelativeRoot(), fileName);
        saveImage(path, destinationPath);
        return "/" + imageCategory.getRelativeRoot() + fileName;
    }

    /**
     * Try to move and then try to copy if move failed
     */
    private void saveImage(Path sourcePath, Path destinationPath) {
        try {
            Files.move(sourcePath, destinationPath, REPLACE_EXISTING);
        } catch (IOException | RuntimeException ex) {
            logger.log(Level.WARNING, String.format("Move failed from %s to %s. Try to copy...", sourcePath, destinationPath), ex);
            try {
                Files.copy(sourcePath, destinationPath, REPLACE_EXISTING);
            } catch(IOException e) {
                ApplicationException applicationException = new ApplicationException("Can't save image: " + destinationPath, e);
                applicationException.addSuppressed(ex);
                throw applicationException;
            }
        }
        logger.log(Level.INFO, "Saved image: {0}", destinationPath);
    }

    @Override
    public void deletePublicImage(String url) {
        Path destinationPath = Paths.get(mediaRoot + url.substring(1));
        try {
            Files.deleteIfExists(destinationPath);
        } catch (IOException | RuntimeException e) {
            logger.log(Level.SEVERE, "Delete public image failed: "+destinationPath, e);
        }
    }

    @Override
    public OriginalImage getOriginalImage(String originalUrl) {
        Path originalPath = Paths.get(storageRoot + originalUrl);
        try {
            return new OriginalImage(
                    Files.newInputStream(originalPath),
                    Files.size(originalPath),
                    originalPath.getFileName().toString());
        } catch (IOException ex) {
            throw new ApplicationException(format("Can't get access to original image: %s", originalPath), ex);
        }
    }
}
