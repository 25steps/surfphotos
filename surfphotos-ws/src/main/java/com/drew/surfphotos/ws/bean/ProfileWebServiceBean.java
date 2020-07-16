package com.drew.surfphotos.ws.bean;

import com.drew.surfphotos.common.converter.ModelConverter;
import com.drew.surfphotos.model.Pageable;
import com.drew.surfphotos.model.domain.Photo;
import com.drew.surfphotos.model.domain.Profile;
import com.drew.surfphotos.service.PhotoService;
import com.drew.surfphotos.service.ProfileService;
import com.drew.surfphotos.ws.ProfileWebService;
import com.drew.surfphotos.ws.error.ExceptionMapperInterceptor;
import com.drew.surfphotos.ws.model.ProfilePhotoSOAP;
import com.drew.surfphotos.ws.model.ProfilePhotosSOAP;
import com.drew.surfphotos.ws.model.ProfileSOAP;

import javax.ejb.ConcurrencyManagement;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.jws.WebService;

import java.util.List;

import static javax.ejb.ConcurrencyManagementType.BEAN;

@Singleton
@ConcurrencyManagement(BEAN)
@WebService(portName = "ProfileServicePort",
        serviceName = "ProfileService",
        targetNamespace = "http://soap.surfphotos.com/ws/ProfileService?wsdl",
        endpointInterface = "com.drew.surfphotos.ws.ProfileWebService")
@Interceptors(ExceptionMapperInterceptor.class)
public class ProfileWebServiceBean implements ProfileWebService {
    @EJB
    private ProfileService profileService;

    @EJB
    private PhotoService photoService;

    @Inject
    private ModelConverter modelConverter;

    @Override
    public ProfileSOAP findById(Long id, boolean withPhotos, int limit) {
        Profile profile = profileService.findById(id);
        ProfileSOAP result = modelConverter.convert(profile, ProfileSOAP.class);
        if (withPhotos) {
            List<Photo> photos = photoService.findProfilePhotos(profile.getId(), new Pageable(limit));
            result.setPhotos(modelConverter.convertList(photos, ProfilePhotoSOAP.class));
        }
        return result;
    }

    @Override
    public ProfilePhotosSOAP findProfilePhotos(Long profileId, int page, int limit) {
        List<Photo> photos = photoService.findProfilePhotos(profileId, new Pageable(page, limit));
        ProfilePhotosSOAP result = new ProfilePhotosSOAP();
        result.setPhotos(modelConverter.convertList(photos, ProfilePhotoSOAP.class));
        return result;
    }
}
