package com.gorkemgok.example.jackson.generic;

public class MyBody {

    private int someBodyField;

    private String message;

    public MyBody(int someBodyField, String message) {
        this.someBodyField = someBodyField;
        this.message = message;
    }

    public MyBody() {
    }

    public int getSomeBodyField() {
        return someBodyField;
    }

    public void setSomeBodyField(int someBodyField) {
        this.someBodyField = someBodyField;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
