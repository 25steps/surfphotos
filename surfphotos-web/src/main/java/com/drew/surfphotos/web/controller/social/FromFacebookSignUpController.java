package com.drew.surfphotos.web.controller.social;


import com.drew.surfphotos.common.annotation.qualifier.Facebook;
import com.drew.surfphotos.service.SocialService;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;

/**
 *Логика поведения такая же как и у google+, но отличие в объекте, который используется,
 *  поэтому создан абстрактный контролер чтобы не дублировать код
 */
@WebServlet(urlPatterns = "/from/facebook", loadOnStartup = 1)
public class FromFacebookSignUpController extends AbstractSignUpController{

    @Inject
    @Override
    protected void setSocialService(@Facebook SocialService socialService) {
        this.socialService = socialService;
    }
}
