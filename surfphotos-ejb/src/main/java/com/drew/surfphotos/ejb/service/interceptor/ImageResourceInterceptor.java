package com.drew.surfphotos.ejb.service.interceptor;

import com.drew.surfphotos.model.ImageResource;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Interceptor
public class ImageResourceInterceptor {
    @AroundInvoke
    public Object aroundProcessImageResource(InvocationContext ic) throws Exception {
        try(ImageResource imageResource = (ImageResource) ic.getParameters()[0]) {
            return ic.proceed();
        }
    }
}
