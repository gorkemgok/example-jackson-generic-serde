package com.gorkemgok.example.jackson.generic;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class Main {

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        MyBody myBody = new MyBody(2, "Hello Jackson");
        MyExchange<MyBody> myExchange = new MyExchange<MyBody>(myBody, 5);

        String json = objectMapper.writeValueAsString(myExchange);
        System.out.println(json);

        MyExchange myDeserializedExchange = objectMapper.readValue(json, MyExchange.class);
        System.out.println(myDeserializedExchange.getBody().getClass());

        MyExchange<MyBody> myDeserializedExchange2 = objectMapper.readValue(json, MyExchange.class);
        System.out.println(myDeserializedExchange2.getBody().getClass());

        MyExchange<MyBody> myDeserializedExchange3 = Main.<MyBody>deserializeMyExchange(json);
        System.out.println(myDeserializedExchange3.getBody().getClass());

        MyExchange<MyBody> myDeserializedExchange4 = Main.deserializeMyExchange2(json, MyBody.class);
        System.out.println(myDeserializedExchange4.getBody().getClass());

    }

    public static <T> MyExchange<T> deserializeMyExchange(String json) throws IOException {
        MyExchange<T> myExchange = objectMapper.readValue(json, new TypeReference<MyExchange<T>>(){});
        return myExchange;
    }

    public static <T> MyExchange<T> deserializeMyExchange2(String json, Class<T> clazz) throws IOException {
        MyExchange<T> myExchange = objectMapper.readValue(json, new TypeReference<MyExchange<T>>(){});
        return myExchange;
    }

}
