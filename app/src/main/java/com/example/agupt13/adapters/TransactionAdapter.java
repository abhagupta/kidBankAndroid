package com.example.agupt13.adapters;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.agupt13.myapplication.R;
import com.example.agupt13.objects.Kid;
import com.example.agupt13.objects.Transaction;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by agupt13 on 1/26/16.
 */
public class TransactionAdapter extends ArrayAdapter<Transaction> {

    private static class ViewHolder {
        private TextView itemView;
    }

    public TransactionAdapter(Context context,ArrayList<Transaction> transactions) {
        super(context, 0, transactions);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Transaction trans = getItem(position);
        // ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext())
                    .inflate(R.layout.activity_dashboard, parent, false);

        }

       if(trans != null){
           TextView tt1 = (TextView) convertView.findViewById(R.id.title);
           TextView tt2 = (TextView) convertView.findViewById((R.id.summary));

           if(tt1 != null){
               tt1.setText(trans.getType());
           }

           if(tt2 != null) {
               tt2.setText(String.valueOf(trans.getValue()));
           }

       }


        return convertView;
    }
}
