package com.drew.surfphotos.web.controller;

import com.drew.surfphotos.service.PhotoService;
import com.drew.surfphotos.web.util.UrlExtractorUtils;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.drew.surfphotos.web.util.RoutingUtils.redirectToUrl;

@WebServlet(urlPatterns = "/preview/*", loadOnStartup = 1)
public class PreviewPhotoController extends HttpServlet {
    @EJB
    private PhotoService photoService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long photoId = Long.parseLong(UrlExtractorUtils.getPathVariableValue(req.getRequestURI(), "/preview/", ".jpg"));
        String redirectUrl = photoService.viewLargePhoto(photoId);
        redirectToUrl(redirectUrl, req, resp);
    }
}
