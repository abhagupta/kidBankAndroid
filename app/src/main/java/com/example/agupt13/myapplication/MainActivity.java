package com.example.agupt13.myapplication;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.JsonReader;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agupt13.adapters.KidAdapter;
import com.example.agupt13.objects.Kid;
import com.example.agupt13.utils.GetHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView listview = (ListView) findViewById(R.id.listViewKids);

        new GetKids().execute();

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final Kid kid = (Kid) parent.getItemAtPosition(position);
                OpenDashBoard(kid);

            }

        });
    }


    public void OpenDashBoard(Kid kidsName) {
        Intent intent;
        intent = new Intent(this, Dashboard.class);
        intent.putExtra("KIDS_NAME", kidsName.getName());
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class GetKids extends AsyncTask<ArrayList<Kid>, ArrayList<Kid>, ArrayList<Kid>> {
        public AsyncResponse delegate = null;

        protected ArrayList<Kid> doInBackground(ArrayList<Kid>... params) {
            String hostUrl = "http://172.29.237.169:5000/abhagupta/kids";
            ArrayList<Kid> kids = new ArrayList<Kid>();
            try {
                URL url = new URL(hostUrl);
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
                kids = readJsonStream(stream);


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return kids;
        }


        protected void onPostExecute(ArrayList<Kid> results) {

            KidAdapter adapter1 = new KidAdapter(MainActivity.this, results);
            ListView listView = (ListView) findViewById(R.id.listViewKids);
            listView.setAdapter(adapter1);

        }

        public ArrayList<Kid> readJsonStream(InputStream in) throws IOException {
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
                messages.add(readMessage(reader));
            }
            reader.endArray();
            return messages;
        }

        public Kid readMessage(JsonReader reader) throws IOException {
            String kidName = null;
            String totalrewards = null;
            List geo = null;

            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if (name.equals("name")) {
                    kidName = reader.nextString();
                } else if (name.equals("totalRewards")) {
                    totalrewards = reader.nextString();
                } else {
                    reader.skipValue();
                }
            }
            reader.endObject();
            Kid kid = new Kid(kidName, totalrewards);

            return kid;

        }

    }
}


