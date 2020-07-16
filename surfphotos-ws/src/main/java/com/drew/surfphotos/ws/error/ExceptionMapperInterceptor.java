package com.drew.surfphotos.ws.error;

import com.drew.surfphotos.exception.BusinessException;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPFault;
import javax.xml.ws.soap.SOAPFaultException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Interceptor
public class ExceptionMapperInterceptor {
    @Inject
    private Logger logger;

    @AroundInvoke
    public Object aroundProcessImageResource(InvocationContext ic) throws Exception {
        validateParameters(ic.getParameters());
        try {
            return ic.proceed();
        } catch (Exception e) {
            if (e instanceof BusinessException) {
                logger.log(Level.WARNING, "Ws request failed: {0}", e.getMessage());
                throw new SOAPFaultException(createSOAPFault(e.getMessage(), true));
            } else {
                logger.log(Level.SEVERE, "Ws request failed: " + e.getMessage(), e);
                throw new SOAPFaultException(createSOAPFault("Internal error", false));
            }
        }
    }

    private void validateParameters(Object[] parameters) throws SOAPException {
        for (int i = 0; i < parameters.length; i++) {
            if(parameters[i] == null) {
                throw new SOAPFaultException(createSOAPFault("Parameter ["+i+"] is invalid", true));
            }
        }
    }

    private SOAPFault createSOAPFault(String message, boolean isClientError) throws SOAPException {
        String errorType = isClientError ? "Client" : "Server";
        SOAPFactory soapFactory = SOAPFactory.newInstance();
        return soapFactory.createFault(message, new QName("http://schemas.xmlsoap.org/soap/envelope/", errorType, ""));
    }
}
