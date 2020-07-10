package com.drew.surfphotos.web.controller.logged;

import com.drew.surfphotos.model.domain.Profile;
import com.drew.surfphotos.web.form.ProfileForm;
import com.drew.surfphotos.web.security.SecurityUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.drew.surfphotos.web.util.RoutingUtils.forwardToPage;

@WebServlet(urlPatterns = "/edit", loadOnStartup = 1)
public class EditProfileController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Profile profile = SecurityUtils.getCurrentProfile();
        req.setAttribute("profile", new ProfileForm(profile));
        forwardToPage("edit", req, resp);
    }
}
