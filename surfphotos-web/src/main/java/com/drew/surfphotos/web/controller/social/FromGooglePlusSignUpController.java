package com.drew.surfphotos.web.controller.social;

import com.drew.surfphotos.common.annotation.qualifier.GooglePlus;
import com.drew.surfphotos.service.SocialService;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = "/from/google-plus", loadOnStartup = 1)
public class FromGooglePlusSignUpController extends AbstractSignUpController{

    @Inject
    @Override
    protected void setSocialService(@GooglePlus SocialService socialService) {
        this.socialService = socialService;
    }
}
