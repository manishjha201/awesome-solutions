package com.eshop.app.utils;

import com.google.gson.Gson;

public class JsonConverter {
    private static final Gson gson = new Gson(); // Can be reused

    public static <T> T fromJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }
}
