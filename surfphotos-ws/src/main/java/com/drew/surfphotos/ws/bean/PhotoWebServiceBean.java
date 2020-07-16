package com.drew.surfphotos.ws.bean;

import com.drew.surfphotos.common.converter.ModelConverter;
import com.drew.surfphotos.common.converter.UrlConveter;
import com.drew.surfphotos.model.OriginalImage;
import com.drew.surfphotos.model.Pageable;
import com.drew.surfphotos.model.SortMode;
import com.drew.surfphotos.model.domain.Photo;
import com.drew.surfphotos.service.PhotoService;
import com.drew.surfphotos.ws.PhotoWebService;
import com.drew.surfphotos.ws.error.ExceptionMapperInterceptor;
import com.drew.surfphotos.ws.model.ImageLinkSOAP;
import com.drew.surfphotos.ws.model.PhotoSOAP;
import com.drew.surfphotos.ws.model.PhotosSOAP;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import static com.drew.surfphotos.model.SortMode.POPULAR_AUTHOR;
import static com.drew.surfphotos.model.SortMode.POPULAR_PHOTO;
import static javax.ejb.ConcurrencyManagementType.BEAN;

@MTOM
@Singleton
@ConcurrencyManagement(BEAN)
@WebService(portName = "PhotoServicePort",
        serviceName = "PhotoService",
        targetNamespace = "http://soap.surfphotos.com/ws/PhotoService?wsdl",
        endpointInterface = "com.drew.surfphotos.ws.PhotoWebService")
@Interceptors(ExceptionMapperInterceptor.class)
public class PhotoWebServiceBean implements PhotoWebService {
    @EJB
    private PhotoService photoService;

    @Inject
    private ModelConverter modelConverter;

    @Inject
    private UrlConveter urlConveter;

    @Override
    public PhotosSOAP findAllOrderByPhotoPopularity(int page, int limit, boolean withTotal) {
        return findAll(POPULAR_PHOTO, page, limit, withTotal);
    }

    @Override
    public PhotosSOAP findAllOrderByAuthorPopularity(int page, int limit, boolean withTotal) {
        return findAll(POPULAR_AUTHOR, page, limit, withTotal);
    }

    private PhotosSOAP findAll(SortMode sortMode, int page, int limit, boolean withTotal) {
        List<Photo> photos = photoService.findPopularPhotos(sortMode, new Pageable(page, limit));
        PhotosSOAP result = new PhotosSOAP();
        result.setPhotos(modelConverter.convertList(photos, PhotoSOAP.class));
        if (withTotal) {
            result.setTotal(photoService.countAllPhotos());
        }
        return result;
    }

    @Override
    public ImageLinkSOAP viewLargePhoto(Long photoId) {
        String relativeUrl = photoService.viewLargePhoto(photoId);
        String absoluteUrl = urlConveter.convert(relativeUrl);
        return new ImageLinkSOAP(absoluteUrl);
    }

    @Override
    public DataHandler downloadOriginalImage(Long photoId) {
        OriginalImage originalImage = photoService.downloadOriginalImage(photoId);
        return new DataHandler(new OriginalImageDataSource(originalImage));
    }

    private static class OriginalImageDataSource implements DataSource {

        private final OriginalImage originalImage;

        public OriginalImageDataSource(OriginalImage originalImage) {
            this.originalImage = originalImage;
        }

        @Override
        public InputStream getInputStream() throws IOException {
            return originalImage.getIn();
        }

        @Override
        public OutputStream getOutputStream() throws IOException {
            throw new UnsupportedOperationException("OutputStream is not supported");
        }

        @Override
        public String getContentType() {
            return "image/jpeg";
        }

        @Override
        public String getName() {
            return originalImage.getName();
        }
    }
}
