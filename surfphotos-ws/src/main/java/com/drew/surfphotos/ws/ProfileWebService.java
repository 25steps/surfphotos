package com.drew.surfphotos.ws;

import com.drew.surfphotos.ws.model.ProfilePhotosSOAP;
import com.drew.surfphotos.ws.model.ProfileSOAP;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

@WebService(targetNamespace = "http://soap.myphotos.com/ws/ProfileService?wsdl")
public interface ProfileWebService {
    @WebMethod
    @WebResult(name = "profile")
    public ProfileSOAP findById(
            @WebParam(name = "id") Long id,
            @WebParam(name = "withPhotos") boolean withPhotos,
            @WebParam(name = "limit") int limit);

    @WebMethod
    @WebResult(name = "profilePhotos")
    public ProfilePhotosSOAP findProfilePhotos(
            @WebParam(name = "profileId") Long profileId,
            @WebParam(name = "page") int page,
            @WebParam(name = "limit") int limit);
}
