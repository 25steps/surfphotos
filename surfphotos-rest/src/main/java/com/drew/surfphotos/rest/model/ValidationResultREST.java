package com.drew.surfphotos.rest.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.xml.bind.annotation.XmlType;
import java.util.List;

@ApiModel("ValidationResult")
public class ValidationResultREST extends ErrorMessageREST{

    @ApiModelProperty(required = true)
    private List<ValidationItemREST> items;

    public ValidationResultREST() {
        super("Validation error", true);
    }

    public ValidationResultREST(List<ValidationItemREST> items) {
        this();
        this.items = items;
    }

    public List<ValidationItemREST> getItems() {
        return items;
    }

    public void setItems(List<ValidationItemREST> items) {
        this.items = items;
    }
}
