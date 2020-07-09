package com.drew.surfphotos.common.model;

import com.drew.surfphotos.exception.ApplicationException;
import com.drew.surfphotos.exception.ValidationException;
import com.drew.surfphotos.model.ImageResource;

import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class AbstractMimeTypeImageResource implements ImageResource {
    private Path tempPath;

    protected abstract String getContentType();

    protected String getExtension() {
        String contentType = getContentType();
        if ("image/jpeg".equalsIgnoreCase(contentType)) {
            return "jpg";
        } else if ("image/png".equalsIgnoreCase(contentType)) {
            return "png";
        } else {
            throw new ValidationException("Only JPEG/JPG and PNG formats supported. Current format is " + contentType);
        }
    }

    protected abstract void copyContent() throws Exception;

    @Override
    public final Path getTempPath() {
        if (tempPath == null) {
            tempPath = TempFileFactory.createTempFile(getExtension());
            try {
                copyContent();
            } catch (Exception e) {
                throw new ApplicationException(
                        String.format("Can't copy content from %s to temp file %s", toString(), tempPath), e);
            }
        }
        return tempPath;
    }

    @Override
    public abstract String toString();

    @Override
    public final void close() {
        TempFileFactory.deleteTempFile(tempPath);
        try {
            deleteTempResources();
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.WARNING, "Can't delete temp resource from "+toString(), ex);
        }
    }

    protected abstract void deleteTempResources() throws Exception;
}
