package com.gorkemgok.example.jackson.generic;

public class MyBody {

    private int someOtherField;

    private String message;

    public MyBody(int someOtherField, String message) {
        this.someOtherField = someOtherField;
        this.message = message;
    }

    public MyBody() {
    }

    public int getSomeOtherField() {
        return someOtherField;
    }

    public void setSomeOtherField(int someOtherField) {
        this.someOtherField = someOtherField;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
