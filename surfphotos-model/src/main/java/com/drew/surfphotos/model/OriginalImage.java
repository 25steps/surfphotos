package com.drew.surfphotos.model;

import java.io.InputStream;
import java.util.Objects;

/**
 * Объект оригинальной картинки, который будет загружаться с сервера
 */
public class OriginalImage {
    private final InputStream in;
    private final long size;
    private final String name;

    public OriginalImage(InputStream in, long size, String name) {
        this.in = Objects.requireNonNull(in);
        this.size = size;
        this.name = Objects.requireNonNull(name);
    }

    public InputStream getIn() {
        return in;
    }

    public long getSize() {
        return size;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "OriginalImage{" +
                "in=" + in +
                ", size=" + size +
                ", name='" + name + '\'' +
                '}';
    }
}
