package com.example.agupt13.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.agupt13.myapplication.R;
import com.example.agupt13.objects.Kid;

import java.util.ArrayList;

/**
 * Created by agupt13 on 1/25/16.
 */
public class KidAdapter extends ArrayAdapter<Kid> {



    private static class ViewHolder {
        private TextView itemView;
    }

    public KidAdapter(Context context,ArrayList<Kid> kids) {
        super(context, 0, kids);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Kid kid = getItem(position);
       // ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext())
                    .inflate(R.layout.listitem_kids, parent, false);

        }

        TextView name = (TextView) convertView.findViewById(R.id.kids_name);
        TextView rewards = (TextView) convertView.findViewById(R.id.reward_balance);

        name.setText(kid.getName());
        rewards.setText(kid.getRewards());

        return convertView;
    }
}
