package com.gorkemgok.example.jackson.generic;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        MyBody myBody = new MyBody(2, "Hello Jackson");
        MyExchange<MyBody> myExchange = new MyExchange<MyBody>(myBody, 5);

        String json = objectMapper.writeValueAsString(myExchange);
        System.out.println(json);

        MyExchange myDeserializedExchange = objectMapper.readValue(json, MyExchange.class);
        System.out.println(myDeserializedExchange.getBody().getClass());

        MyExchange<MyBody> myDeserializedExchange2 = objectMapper.readValue(json, MyExchange.class);
        System.out.println(myDeserializedExchange2.getBody().getClass());


    }

}
