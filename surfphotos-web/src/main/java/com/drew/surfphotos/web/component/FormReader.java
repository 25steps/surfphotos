package com.drew.surfphotos.web.component;

import com.drew.surfphotos.exception.ApplicationException;
import org.apache.commons.beanutils.BeanUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;

@ApplicationScoped
public class FormReader {
    public <T> T readForm(HttpServletRequest request, Class<T> formClass) {
        try {
            T form = formClass.newInstance();
            BeanUtils.populate(form, request.getParameterMap());
            return form;
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new ApplicationException(e);
        }
    }
}
