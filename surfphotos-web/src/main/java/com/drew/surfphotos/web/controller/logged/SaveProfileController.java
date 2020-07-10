package com.drew.surfphotos.web.controller.logged;

import com.drew.surfphotos.common.annotation.group.ProfileUpdateGroup;
import com.drew.surfphotos.model.domain.Profile;
import com.drew.surfphotos.service.ProfileService;
import com.drew.surfphotos.web.security.SecurityUtils;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = "/save", loadOnStartup = 1)
public class SaveProfileController extends AbstractProfileSaveController {
    @EJB
    private ProfileService profileService;

    @Override
    protected Class<?>[] getValidationGroups() {
        return new Class<?>[] {ProfileUpdateGroup.class};
    }

    @Override
    protected String getBackToEditView() {
        return "edit";
    }

    @Override
    protected Profile getCurrentProfile() {
        return SecurityUtils.getCurrentProfile();
    }

    @Override
    protected void saveProfile(Profile profile) {
        profileService.update(profile);
    }
}
