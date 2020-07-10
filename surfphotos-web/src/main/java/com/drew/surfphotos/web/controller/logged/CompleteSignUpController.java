package com.drew.surfphotos.web.controller.logged;

import com.drew.surfphotos.common.annotation.group.SignUpGroup;
import com.drew.surfphotos.model.domain.Profile;
import com.drew.surfphotos.web.component.ProfileSignUpServiceProxy;
import com.drew.surfphotos.web.security.SecurityUtils;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = "/sign-up/complete", loadOnStartup = 1)
public class CompleteSignUpController extends AbstractProfileSaveController {
    @Inject
    private ProfileSignUpServiceProxy profileSignUpService;

    @Override
    protected Class<?>[] getValidationGroups() {
        return new Class<?>[] {SignUpGroup.class};
    }

    @Override
    protected String getBackToEditView() {
        return "sign-up";
    }

    @Override
    protected Profile getCurrentProfile() {
        return profileSignUpService.getCurrentProfile();
    }

    @Override
    protected void saveProfile(Profile profile) {
        profileSignUpService.completeSignUp();
        reloginWithUserRole(profile);
    }

    private void reloginWithUserRole(Profile profile) {
        SecurityUtils.logout();
        SecurityUtils.authentificate(profile);
    }
}
