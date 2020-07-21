package com.drew.surfphotos.rest.config;

import io.swagger.jaxrs.config.BeanConfig;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import static com.drew.surfphotos.rest.Constants.CURRENT_VERSION;

@ApplicationPath(CURRENT_VERSION)
public class ApplicationConfig extends Application {
    public ApplicationConfig() {
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.0");
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setTitle("SurfPhotos.com API (Version 1.0)");
        beanConfig.setBasePath("/v1");
        beanConfig.setResourcePackage("com.drew.surfphotos.rest");

        beanConfig.setPrettyPrint(true);
        beanConfig.setLicenseUrl("http://www.apache.org/licenses/LICENSE-2.0");
        beanConfig.setScan(true);
    }
}
