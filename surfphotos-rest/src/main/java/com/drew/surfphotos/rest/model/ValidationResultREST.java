package com.drew.surfphotos.rest.model;

import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlType(name="")
public class ValidationResultREST extends ErrorMessageREST{

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
