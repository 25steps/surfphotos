package com.drew.surfphotos.ws;

import com.drew.surfphotos.ws.model.ImageLinkSOAP;
import com.drew.surfphotos.ws.model.PhotosSOAP;

import javax.activation.DataHandler;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * Данный интерфейс является веб сервисом. Бидется он будет http://soap.surfphotos.com/ws/PhotoService?wsdl
 * если что-то протестить то glassfish предоставляет http://soap.surfphotos.com/ws/PhotoService?Tester
 */
@WebService(targetNamespace = "http://soap.surfphotos.com/ws/PhotoService?wsdl")
public interface PhotoWebService {
    @WebMethod
    @WebResult(name = "photos")
    public PhotosSOAP findAllOrderByPhotoPopularity(
            @WebParam(name = "page") int page,
            @WebParam(name = "limit") int limit,
            @WebParam(name = "withTotal") boolean withTotal);

    @WebMethod
    @WebResult(name = "photos")
    public PhotosSOAP findAllOrderByAuthorPopularity(
            @WebParam(name = "page") int page,
            @WebParam(name = "limit") int limit,
            @WebParam(name = "withTotal") boolean withTotal);

    @WebMethod
    @WebResult(name = "imageLink")
    public ImageLinkSOAP viewLargePhoto(
            @WebParam(name = "photoId") Long photoId);

    @WebMethod
    @WebResult(name = "originalImage")
    public DataHandler downloadOriginalImage(
            @WebParam(name = "photoId") Long photoId);
}
