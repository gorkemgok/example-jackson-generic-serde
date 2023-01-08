package com.gorkemgok.example.jackson.generic;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

public class MyExchange<T> {

    @JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_ARRAY, use = JsonTypeInfo.Id.CLASS, property = "@bodyClass")
    private T body;

    private int someOtherField;

    public MyExchange(T body, int someOtherField) {
        this.body = body;
        this.someOtherField = someOtherField;
    }

    public MyExchange() {
    }

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
