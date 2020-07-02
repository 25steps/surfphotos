package com.drew.surfphotos.web.controller;

import com.drew.surfphotos.model.OriginalImage;
import com.drew.surfphotos.service.PhotoService;
import com.drew.surfphotos.web.util.UrlExtractorUtils;
import org.apache.commons.io.IOUtils;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@WebServlet(urlPatterns = "/download/*", loadOnStartup = 1)
public class DownloadPhotoController extends HttpServlet {
    @EJB
    private PhotoService photoService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long photoId = Long.parseLong(UrlExtractorUtils.getPathVariableValue(req.getRequestURI(), "/download/", ".jpg"));
        OriginalImage originalImage = photoService.downloadOriginalImage(photoId);

        resp.setHeader("Content-Disposition", "attachment; filename="+originalImage.getName());
        resp.setContentType(getServletContext().getMimeType(originalImage.getName()));
        resp.setContentLengthLong(originalImage.getSize());
        try(InputStream in = originalImage.getIn();
            OutputStream out = resp.getOutputStream()) {
            IOUtils.copyLarge(in, out);
        }
    }
}
