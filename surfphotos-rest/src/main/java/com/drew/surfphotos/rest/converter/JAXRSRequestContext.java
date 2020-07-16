package com.drew.surfphotos.rest.converter;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.commons.fileupload.RequestContext;

public class JAXRSRequestContext implements RequestContext {
    private final MultivaluedMap<String, String> httpHeaders;
    private final InputStream entityStream;
    private final String contentType;

    public JAXRSRequestContext(MultivaluedMap<String, String> httpHeaders, InputStream entityStream, String contentType) {
        this.httpHeaders = httpHeaders;
        this.entityStream = entityStream;
        this.contentType = contentType;
    }

    @Override
    public String getCharacterEncoding() {
        return StandardCharsets.UTF_8.name();
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public int getContentLength() {
        return Integer.parseInt(httpHeaders.getFirst("Content-Length"));
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return entityStream;
    }
}

