package com.drew.surfphotos.rest.converter;

import com.drew.surfphotos.common.model.ListMap;
import com.drew.surfphotos.rest.model.ValidationItemREST;
import com.drew.surfphotos.rest.model.ValidationResultREST;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.ConstraintViolation;
import javax.validation.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@ApplicationScoped
public class ConstraintViolationConverter {

    public <T> ValidationResultREST convert(Set<ConstraintViolation<T>> violations) {
        ListMap<String, String> listMap = new ListMap<>();
        for(ConstraintViolation<T> violation : violations) {
            for(Path.Node node : violation.getPropertyPath()) {
                listMap.add(node.getName(), violation.getMessage());
            }
        }
        return createValidationResult(listMap);
    }

    private ValidationResultREST createValidationResult(ListMap<String, String> listMap) {
        List<ValidationItemREST> list = new ArrayList<>();
        for(Map.Entry<String, List<String>> entry : listMap.toMap().entrySet()) {
            list.add(new ValidationItemREST(entry.getKey(), entry.getValue()));
        }
        return new ValidationResultREST(list);
    }
}
