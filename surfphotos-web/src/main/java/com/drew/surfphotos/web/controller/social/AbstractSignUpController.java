package com.drew.surfphotos.web.controller.social;

import com.drew.surfphotos.model.domain.Profile;
import com.drew.surfphotos.service.ProfileService;
import com.drew.surfphotos.service.SocialService;
import com.drew.surfphotos.web.component.ProfileSignUpServiceProxy;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static com.drew.surfphotos.web.util.RoutingUtils.redirectToUrl;

public abstract class AbstractSignUpController extends HttpServlet{

    @EJB
    protected ProfileService profileService;

    @Inject
    protected ProfileSignUpServiceProxy profileSignUpService;

    protected SocialService socialService;

    protected abstract void setSocialService(SocialService socialService);

    @Override
    protected final void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Optional<String> code = Optional.ofNullable(req.getParameter("code"));
        if (code.isPresent()) {
            processSignUp(code.get(), req, resp);
        } else {
            redirectToUrl("/", req, resp);
        }
    }

    private void processSignUp(String code, HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Profile signUpProfile = socialService.fetchProfile(code);
        Optional<Profile> profileOptional = profileService.findByEmail(signUpProfile.getEmail());
        if (profileOptional.isPresent()) {
            Profile profile = profileOptional.get();
            //TODO Authentificate
            redirectToUrl("/" + profile.getUid(), req, resp);
        } else {
            //TODO Authentificate
            profileSignUpService.createSignUpProfile(signUpProfile);
            redirectToUrl("/sign-up", req, resp);
        }
    }
}
