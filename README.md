### How to Serialize and Deserialize Generic Java Classes in Jackson

*Jackson version: 2.9.9

When you try to serialize a generic class to JSON, Jackson will serialize the generic type correctly 
with no extra afford but when you deserialize the serialized JSON you will end up 
with `LinkedHashMap` as generic type. The type of the generic type will be lost.

MyExchange.java
```java
package com.gorkemgok.example.jackson.generic;

public class MyExchange<T> {

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
```

MyBody.java
```java
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
```

When you run the code below:

```java
...
ObjectMapper objectMapper = new ObjectMapper();
MyBody myBody = new MyBody(2, "Hello Jackson");
MyExchange<MyBody> myExchange = new MyExchange<MyBody>(myBody, 5);

String json = objectMapper.writeValueAsString(myExchange);
System.out.println(json);

MyExchange myDeserializedExchange = objectMapper.readValue(json, MyExchange.class);
System.out.println(myDeserializedExchange.getBody().getClass());
...
```

Output:
```json
{"body":{"someOtherField":2,"message":"Hello Jackson"},"someOtherField":5}
```
```
class java.util.LinkedHashMap
```

When you want to get `MyExchange<MyBody>` you will get a `ClassCastException`.
```
java.lang.ClassCastException: java.util.LinkedHashMap cannot be cast to com.gorkemgok.example.jackson.generic.MyBody
```

For that reason we should keep type information of generic class in some where.

### @JsonTypeInfo

When you annotate a property with `@JsonTypeInfo` Jackson will keep the class name of the generic object in json and will use this class name while deserializing.
You can see the [javadoc](https://www.javadoc.io/doc/com.fasterxml.jackson.core/jackson-annotations/2.9.9).

```java
...
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@modelClass")
public T getBody() {
    return body;
}
...
```

That's all!
When convert `MyExchance` object to json you will get the following:
```json
{"body":
  {
    "@modelClass":"com.gorkemgok.example.jackson.generic.MyBody",
    "someOtherField":2,
    "message":"Hello Jackson",
  },
  "someOtherField":5}
```
An extra json property `@modelClass` which is specified at `property` field of `@JsonTypeInfo` annotation is appeared. 

When you deserialize json above you will get the correct body type.
```java
...
MyExchange myDeserializedExchange = objectMapper.readValue(json, MyExchange.class);
System.out.println(myDeserializedExchange.getBody().getClass());

MyExchange<MyBody> myDeserializedExchange2 = objectMapper.readValue(json, MyExchange.class);
System.out.println(myDeserializedExchange2.getBody().getClass());

```
Output:
```
class com.gorkemgok.example.jackson.generic.MyBody
class com.gorkemgok.example.jackson.generic.MyBody
```

You can also use `TypeReference` class for type safe methods:
```java
...

public static <T> MyExchange<T> deserializeMyExchange(String json) throws IOException {
    MyExchange<T> myExchange = objectMapper.readValue(json, new TypeReference<T>(){});
    return myExchange;
}

...

MyExchange myDeserializedExchange3 = Main.<MyBody>deserializeMyExchange(json);
System.out.println(myDeserializedExchange3.getBody().getClass());

...
```
or
```java
...

public static <T> MyExchange<T> deserializeMyExchange(String json, Class<T> clazz) throws IOException {
    MyExchange<T> myExchange = objectMapper.readValue(json, new TypeReference<T>(){});
    return myExchange;
}

...

MyExchange myDeserializedExchange3 = Main.deserializeMyExchange(json, MyBody.class);
System.out.println(myDeserializedExchange3.getBody().getClass());

...
```
Output:
```
class com.gorkemgok.example.jackson.generic.MyBody
class com.gorkemgok.example.jackson.generic.MyBody
```