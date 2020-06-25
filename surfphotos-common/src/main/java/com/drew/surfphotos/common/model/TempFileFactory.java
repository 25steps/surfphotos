package com.drew.surfphotos.common.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TempFileFactory {
    public static Path createTempFile(String extension) {
        String uniqueFileName = String.format("%s.%s", UUID.randomUUID(), extension);
        String tempDirectoryPath = System.getProperty("java.io.tmpdir");
        Path tempFilePath = Paths.get(tempDirectoryPath, uniqueFileName);
        try {
            return Files.createFile(tempFilePath);
        } catch (IOException e) {
            throw new CantCreateTempFileException(tempFilePath, e);
        }
    }

    public static void deleteTempFile(Path path) {
        try {
            Files.deleteIfExists(path);
        } catch (IOException | RuntimeException ex) {
            Logger.getLogger("TempFileEraser").log(Level.WARNING, "Can't delete temp file: " + path, ex);
        }
    }

    private static class CantCreateTempFileException extends IllegalStateException {
        private CantCreateTempFileException(Path tempFilePath, Throwable throwable) {
            super("Can't create temp file: " + tempFilePath, throwable);
        }
    }
}
