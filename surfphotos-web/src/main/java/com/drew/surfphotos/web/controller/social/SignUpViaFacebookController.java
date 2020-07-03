package com.drew.surfphotos.web.controller.social;

import com.drew.surfphotos.common.annotation.qualifier.Facebook;
import com.drew.surfphotos.service.SocialService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.drew.surfphotos.web.util.RoutingUtils.redirectToUrl;

/**
 * При помощи квалификатора @Facebook получаем соответствующий url FacebookSocialService и выполнеям редирект
 */
@WebServlet(urlPatterns = "/sign-up/facebook", loadOnStartup = 1)
public class SignUpViaFacebookController extends HttpServlet {

    @Inject
    @Facebook
    private SocialService socialService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        redirectToUrl(socialService.getAuthorizeUrl(), req, resp);
    }
}
