package com.drew.surfphotos.web.controller.logged;

import com.drew.surfphotos.model.domain.Profile;
import com.drew.surfphotos.web.component.ProfileSignUpServiceProxy;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.drew.surfphotos.web.util.RoutingUtils.forwardToPage;

@WebServlet(urlPatterns = "/sign-up", loadOnStartup = 1)
public class CurrentSignUpProgressController extends HttpServlet{

    @Inject
    private ProfileSignUpServiceProxy profileSignUpService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Profile profile = profileSignUpService.getCurrentProfile();
        req.setAttribute("profile", profile);
        forwardToPage("sign-up", req, resp);
    }
}
