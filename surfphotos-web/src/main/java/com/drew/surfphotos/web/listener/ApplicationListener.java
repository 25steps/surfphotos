package com.drew.surfphotos.web.listener;

import com.drew.surfphotos.common.annotation.qualifier.GooglePlus;
import com.drew.surfphotos.service.SocialService;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.logging.Logger;

@WebListener
public class ApplicationListener implements ServletContextListener {

    @Inject
    private Logger logger;

    @Inject
    @GooglePlus
    private SocialService googlePlusSocialService;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setAttribute("googlePlusClientId", googlePlusSocialService.getClientId());
        logger.info("Application 'surfphotos' initialized googlePlusClientId=" + googlePlusSocialService.getClientId());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("Application 'surfphotos' destroyed");
    }

}
