package com.drew.surfphotos.rest.config;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import static com.drew.surfphotos.rest.Constants.CURRENT_VERSION;

@ApplicationPath(CURRENT_VERSION)
public class ApplicationConfig extends Application {

}
