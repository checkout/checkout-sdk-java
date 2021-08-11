package com.checkout;

import java.lang.reflect.Type;

public interface Serializer {
    <T> String toJson(T object);

    <T> T fromJson(String json, Class<T> type);

    <T> T fromJson(String json, Type type);
}
