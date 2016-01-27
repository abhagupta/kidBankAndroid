package com.example.agupt13.utils;

import android.os.AsyncTask;
import android.util.JsonReader;
import android.widget.TextView;

import com.example.agupt13.myapplication.R;
import com.example.agupt13.objects.Kid;
import com.example.agupt13.objects.ModelObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by agupt13 on 1/22/16.
 */
public class GetHelper extends AsyncTask<Object, Void, List<ModelObject>> {

  //  public final String hostUrl = "http://frozen-ocean-1881.herokuapp.com";
  //  public final String hostUrl = "http://10.0.0.73:5000";
    public final String hostUrl = "http://172.29.237.169:5000";

        @Override
        protected List<ModelObject> doInBackground(Object... params) {
            ArrayList<ModelObject> objects = new ArrayList<ModelObject>();

            try {
                URL url = new URL(hostUrl + params[0] );
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                BufferedInputStream in = new BufferedInputStream(urlConnection.getInputStream());
                int bytesRead = 0;

                byte[] contents = new byte[1024];
                String strFileContents = "failed";
                while ((bytesRead = in.read(contents)) != -1) {
                    strFileContents = new String(contents, 0, bytesRead);
                }
                System.out.println("contents :" + strFileContents);
                InputStream stream = new ByteArrayInputStream(strFileContents.getBytes("UTF-8"));
                objects = readJsonStream(stream);


            } catch (Exception e) {
                e.printStackTrace();
            }

            return  objects;

        }

    public ArrayList<ModelObject> readJsonStream(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            return readMessagesArray(reader);
        } finally {
            reader.close();
        }
    }

    public ArrayList readMessagesArray(JsonReader reader) throws IOException {
        ArrayList messages = new ArrayList();

        reader.beginArray();
        while (reader.hasNext()) {
            messages.add(reader);
        }
        reader.endArray();
        return messages;
    }

//    public ModelObject readMessage(JsonReader reader) throws IOException {
//        String kidName = null;
//        String totalrewards = null;
//        List geo = null;
//
//        reader.beginObject();
//        while (reader.hasNext()) {
//            String name = reader.nextName();
//            if (name.equals("name")) {
//                kidName = reader.nextString();
//            } else if (name.equals("totalRewards")) {
//                totalrewards = reader.nextString();
//            } else {
//                reader.skipValue();
//            }
//        }
//        reader.endObject();
//        Kid kid = new Kid(kidName, totalrewards);
//
//        return kid;
//
//    }

}



