package com.drew.surfphotos.web.model;

import com.drew.surfphotos.common.model.AbstractMimeTypeImageResource;

import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class PartImageResource extends AbstractMimeTypeImageResource {

    private final Part part;

    public PartImageResource(Part part) {
        this.part = Objects.requireNonNull(part);
    }

    @Override
    protected String getContentType() {
        return part.getContentType();
    }

    @Override
    protected void copyContent() throws IOException {
        Files.copy(part.getInputStream(), getTempPath(), REPLACE_EXISTING);
    }

    @Override
    public String toString() {
        return String.format("%s(%s)", getClass().getSimpleName(), part);
    }

    @Override
    protected void deleteTempResources() throws IOException {
        part.delete();
    }
}
