package com.example.agupt13.utils;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by agupt13 on 1/22/16.
 */
public class PostHelper  extends AsyncTask<String, String, String> {
   // public final String hostUrl = "http://frozen-ocean-1881.herokuapp.com";
    public final String hostUrl = "http://172.29.237.169:5000";

    protected String doInBackground(String... params) {


        try {
            //URL url = new URL(hostUrl + "/transaction/abhagupta/" + params[0] + "/" + params[1]);
            URL url = new URL(hostUrl + params[0] );
            //HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            //System.out.print(strFileContents);
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("POST");
            c.setRequestProperty("Content-length", "0");
            c.setUseCaches(false);
            c.setAllowUserInteraction(false);
            //c.setConnectTimeout(timeout);
            //c.setReadTimeout(timeout);
            c.connect();

            BufferedInputStream in = new BufferedInputStream(c.getInputStream());

            int bytesRead = 0;

            byte[] contents = new byte[1024];
            String strFileContents = "failed";
            while ((bytesRead = in.read(contents)) != -1) {
                strFileContents = new String(contents, 0, bytesRead);
            }
            return strFileContents;
        } catch (Exception e) {
            e.printStackTrace();
        }
           return "failed";
        }

}