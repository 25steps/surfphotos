package com.drew.surfphotos.common.model;

import com.drew.surfphotos.model.ImageResource;

import java.nio.file.Path;

public class TempImageResource implements ImageResource {
    private final Path path;

    public TempImageResource() {
        this("jpg");
    }

    public TempImageResource(String extension) {
        this.path = TempFileFactory.createTempFile(extension);
    }

    @Override
    public Path getTempPath() {
        return path;
    }

    @Override
    public void close() {
        TempFileFactory.deleteTempFile(path);
    }

    @Override
    public String toString() {
        return String.format("%s(%s)", getClass().getSimpleName(), path);
    }
}
