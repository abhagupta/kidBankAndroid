package com.example.agupt13.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
import android.widget.ListView;
import android.widget.TextView;

import com.example.agupt13.adapters.KidAdapter;
import com.example.agupt13.adapters.TransactionAdapter;
import com.example.agupt13.objects.Kid;
import com.example.agupt13.objects.ModelObject;
import com.example.agupt13.objects.Transaction;
import com.example.agupt13.utils.GetHelper;
import com.example.agupt13.utils.PostHelper;
import com.example.agupt13.utils.Utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class Dashboard extends ActionBarActivity {

  //  public final String hostUrl = "frozen-ocean-1881.herokuapp.com";
    public final String hostUrl = "172.29.237.169:5000";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_transactions) {
            Intent intent;
            intent = new Intent(this, TransactionActivity.class);
            intent.putExtra("KIDS_NAME", "Aditi");
            startActivity(intent);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        final String kidsName = intent.getStringExtra("KIDS_NAME");
        TextView textView = (TextView) findViewById(R.id.title);
        textView.setTextSize(40);
        textView.setText(kidsName);

        new GetRewardInformation().execute("/summary/abhagupta/" + kidsName);

        // find the activity from api right here

        new GetTransactionsForKid().execute(kidsName);

        ListView listview = (ListView) findViewById(R.id.listviewTransactionTypes);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final TranType item = (TranType) parent.getItemAtPosition(position);
                recordTransaction(kidsName, item.name);

            }

        });



    }


    private void recordTransaction(String kidsName, String tranType) {
        new PostReward().execute("/transaction/abhagupta/" + kidsName + "/" + tranType);
    }

    private class GetRewardInformation extends GetHelper {


        protected void onPostExecute(String result) {
            TextView textSummary = (TextView) findViewById(R.id.summary);
            textSummary.setText("Total Rewards : $" + result);
        }


    } // end GetRewardInformation

    private class PostReward extends PostHelper {

        protected void onPostExecute(String result) {
            TextView textSummary = (TextView) findViewById(R.id.summary);
            textSummary.setText("Total Rewards : $" + result);
        }


    } // end PostReward

    public class TranType {
        public String name;
        public Date lastUsed;

        public TranType(String name, Date date) {
            this.name = name;
            this.lastUsed = lastUsed;
        }
    }

    public class TranTypeAdapter extends ArrayAdapter<TranType> {
        private ArrayList<TranType> items;
        private TranViewHolder tranTypeView;

        private class TranViewHolder {
            TextView name;
            TextView lastUsed;
        }

        public TranTypeAdapter(Context context, int tvResId, ArrayList<TranType> items) {
            super(context, tvResId, items);
            this.items = items;
        }

        @Override
        public View getView(int pos, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.listitem_transactiontype, null);
                tranTypeView = new TranViewHolder();
                tranTypeView.name = (TextView) v.findViewById(R.id.transactiontype_name);
                tranTypeView.lastUsed = (TextView) v.findViewById(R.id.last_used);
                v.setTag(tranTypeView);
            } else tranTypeView = (TranViewHolder) v.getTag();

            TranType selectedTranType = items.get(pos);

            if (selectedTranType != null) {
                tranTypeView.name.setText(selectedTranType.name);
                tranTypeView.lastUsed.setText("01/15/2016");
            }

            return v;
        }
    }


    public class GetTransactionsForKid extends AsyncTask<String, Void, ArrayList<Transaction>> {


@Override
        protected ArrayList<Transaction> doInBackground(String... params) {

            String hostUrl = "http://172.29.237.169:5000/transactions/abhagupta/"+params[0];
            ArrayList jsonResponse = new ArrayList();
            ArrayList<Transaction> transactions = new ArrayList<Transaction>();
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
                System.out.println("transactions :" + strFileContents);
                InputStream stream = new ByteArrayInputStream(strFileContents.getBytes("UTF-8"));
                transactions = readJsonStream(stream);



            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return transactions;
        }
        @Override
        protected void onPostExecute(ArrayList<Transaction> results) {

            TransactionAdapter adapter1 = new TransactionAdapter(Dashboard.this, results);
            ListView listView = (ListView) findViewById(R.id.listviewTransactionTypes);
            listView.setAdapter(adapter1);


//            final ListView listview = (ListView) findViewById(R.id.listviewTransactionTypes);
//            String[] values = new String[]{"AllNightSleep", "HelpChores"};

//            final ArrayList<TranType> list = new ArrayList<TranType>();
//            for (int i = 0; i < values.length; ++i) {
//                list.add(new TranType(values[i], new Date()));
//            }
//
//            final TranTypeAdapter adapter = new TranTypeAdapter(this, R.layout.listitem_transactiontype, list);
           listView.setAdapter(adapter1);

//            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//                @Override
//                public void onItemClick(AdapterView<?> parent, final View view,
//                                        int position, long id) {
//                    final TranType item = (TranType) parent.getItemAtPosition(position);
//                    recordTransaction(kidsName, item.name);
//
//                }
//
//            });

        }

        public  ArrayList<Transaction> readJsonStream(InputStream in) throws IOException {
            JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
            try {
                return readMessagesArray(reader);
            } finally {
                reader.close();
            }
        }

        public  ArrayList readMessagesArray(JsonReader reader) throws IOException {
            ArrayList messages = new ArrayList();

            reader.beginArray();
            while (reader.hasNext()) {
                messages.add(readMessage(reader));
            }
            reader.endArray();
            return messages;
        }


        private ModelObject readMessage(JsonReader reader) throws IOException {

            String kidName = null;
            String transactionType = null;
            Long transactionValue = new Long(0) ;
            List geo = null;

            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                if (name.equals("kidsname")) {
                    kidName = reader.nextString();
                } else if (name.equals("type")) {
                    transactionType = reader.nextString();
                } else if (name.equals("value")) {
                    transactionValue  = reader.nextLong();
                }else {
                    reader.skipValue();
                }
            }
            reader.endObject();
            Transaction trans = new Transaction();
            trans.setType(transactionType);
            trans.setValue(transactionValue);


            return trans;

        }
    }

}

