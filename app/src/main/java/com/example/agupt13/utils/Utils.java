package com.example.agupt13.utils;

import android.util.JsonReader;

import com.example.agupt13.objects.Kid;
import com.example.agupt13.objects.ModelObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by agupt13 on 1/26/16.
 */
public  class Utils {

    public static ArrayList<ModelObject> readJsonStream(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return readMessagesArray(reader);
        } finally {
            reader.close();
        }
    }

    public static ArrayList readMessagesArray(JsonReader reader) throws IOException {
        ArrayList messages = new ArrayList();

        reader.beginArray();
        while (reader.hasNext()) {
            messages.add(reader);
        }
        reader.endArray();
        return messages;
    }



}
