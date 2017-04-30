package com.brotherd.poemtrip.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dumingwei on 2017/4/30.
 */
public class GsonUtil {

    private static Gson gson = new Gson();

    private static GsonBuilder gb = new GsonBuilder();

    public static String toString(Object object) {
        gb.disableHtmlEscaping();
        return gb.create().toJson(object);
    }

    public static <T> T toObject(String json, Class<T> classOfT) {
        return gson.fromJson(json, classOfT);
    }

    public static <T> List<T> toList(String json, Class<T> classOfT) {
        List<T> list = new ArrayList<>();
        JsonArray jsonElements = new JsonParser().parse(json).getAsJsonArray();
        for (JsonElement jsonElement : jsonElements) {
            list.add(gson.fromJson(jsonElement, classOfT));
        }
        return list;
    }

}
