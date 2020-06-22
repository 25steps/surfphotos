package com.drew.surfphotos.common.resource;

import javax.enterprise.context.ApplicationScoped;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 */
@ApplicationScoped
public class FileResourceLoader implements ResourceLoader {

    @Override
    public boolean isSupport(String resourceName) {
        try {
            return new File(resourceName).exists();
        } catch (RuntimeException e) {
            return false;
        }
    }

    @Override
    public InputStream getInputStream(String resourceName) throws IOException {
        return new FileInputStream(resourceName);
    }
}
