package com.checkout;

import java.lang.reflect.Type;
import java.util.Map;

public interface Serializer {

    <T> String toJson(T object);

    <T> T fromJson(String json, Class<T> type);

    <T> T fromJson(String json, Type type);

    Map<String, Object> fromJson(String json);

}
