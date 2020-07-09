package com.drew.surfphotos.web.component;

import com.drew.surfphotos.common.model.ListMap;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.ConstraintViolation;
import javax.validation.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;

@ApplicationScoped
public class ConstraintViolationConverter {
    public <T> Map<String, List<String>> convert(Set<ConstraintViolation<T>> violations) {
        ListMap<String, String> listMap = new ListMap<>();
        for(ConstraintViolation<T> violation : violations) {
            for(Path.Node node : violation.getPropertyPath()) {
                listMap.add(node.getName(), violation.getMessage());
            }
        }
        return listMap.toMap();
    }
}
