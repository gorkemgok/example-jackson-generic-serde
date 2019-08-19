package com.gorkemgok.example.jackson.generic;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

public class MyExchange<T> {

    private T body;

    private int someOtherField;

    public MyExchange(T body, int someOtherField) {
        this.body = body;
        this.someOtherField = someOtherField;
    }

    public MyExchange() {
    }

    @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@bodyClass")
    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public int getSomeOtherField() {
        return someOtherField;
    }

    public void setSomeOtherField(int someOtherField) {
        this.someOtherField = someOtherField;
    }
}
