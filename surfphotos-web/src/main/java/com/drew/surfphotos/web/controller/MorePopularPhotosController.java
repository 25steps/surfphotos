package com.drew.surfphotos.web.controller;

import com.drew.surfphotos.model.Pageable;
import com.drew.surfphotos.model.SortMode;
import com.drew.surfphotos.model.domain.Photo;
import com.drew.surfphotos.service.PhotoService;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.drew.surfphotos.web.Constants.PHOTO_LIMIT;
import static com.drew.surfphotos.web.util.RoutingUtils.forwardToFragment;

@WebServlet(urlPatterns = "/photos/popular/more", loadOnStartup = 1)
public class MorePopularPhotosController extends HttpServlet {
    @EJB
    private PhotoService photoService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SortMode sortMode = SortMode.of(req.getParameter("sort"));
        int page = Integer.parseInt(req.getParameter("page"));
        List<Photo> photos = photoService.findPopularPhotos(sortMode, new Pageable(page, PHOTO_LIMIT));
        req.setAttribute("photos", photos);
        req.setAttribute("sortMode", sortMode.name().toLowerCase());
        forwardToFragment("more-photos", req, resp);
    }

}
