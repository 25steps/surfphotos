package com.drew.surfphotos.web.controller.logged;

import com.drew.surfphotos.model.domain.Profile;
import com.drew.surfphotos.web.component.ConstraintViolationConverter;
import com.drew.surfphotos.web.component.FormReader;
import com.drew.surfphotos.web.form.ProfileForm;
import com.drew.surfphotos.web.util.RoutingUtils;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.IOException;
import java.util.Set;

import static com.drew.surfphotos.web.util.RoutingUtils.forwardToPage;

public abstract class AbstractProfileSaveController extends HttpServlet {
    @Resource(lookup = "java:comp/Validator")
    private Validator validator;

    @Inject
    private ConstraintViolationConverter constraintViolationConvertor;

    @Inject
    private FormReader formReader;

    @Override
    protected final void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected final void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ProfileForm profileForm = formReader.readForm(req, ProfileForm.class);
        Set<ConstraintViolation<ProfileForm>> violations = validator.validate(profileForm, getValidationGroups());
        if(violations.isEmpty()) {
            saveChanges(profileForm, req, resp);
        } else {
            backToEditPage(req, profileForm, violations, resp);
        }
    }

    protected abstract Class<?>[] getValidationGroups();

    private void backToEditPage(HttpServletRequest req, ProfileForm profileForm,
                                Set<ConstraintViolation<ProfileForm>> violations, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("profile", profileForm);
        req.setAttribute("violations", constraintViolationConvertor.convert(violations));
        forwardToPage(getBackToEditView(), req, resp);
    }

    protected abstract String getBackToEditView();

    private void saveChanges(ProfileForm profileForm, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Profile profile = getCurrentProfile();
        profileForm.copyToProfile(profile);
        saveProfile(profile);
        RoutingUtils.redirectToUrl("/"+profile.getUid(), req, resp);
    }

    protected abstract Profile getCurrentProfile();

    protected abstract void saveProfile(Profile profile);
}
