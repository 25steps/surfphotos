package com.drew.surfphotos.common.converter.impl;

import com.drew.surfphotos.common.annotation.cdi.Property;
import com.drew.surfphotos.common.converter.UrlConveter;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;

@ApplicationScoped
public class DefaultUrlConveter implements UrlConveter {
    @Inject
    @Property("surfphotos.host")
    private String host;

    @Override
    public String convert(String url) {
        try {
            if (new URI(url).isAbsolute()) {
                return url;
            }
        } catch (URISyntaxException e) {
            // do nothing. Url is relative
        }
        return host + url;
    }
}
