package com.drew.surfphotos.rest.model;

import javax.xml.bind.annotation.XmlType;
import java.util.List;

@XmlType(name="")
public class ValidationItemREST {

    private String field;

    private List<String> messages;

    public ValidationItemREST() {
    }

    public ValidationItemREST(String field, List<String> messages) {
        this.field = field;
        this.messages = messages;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }
}
