package com.example.agupt13.myapplication;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class TransactionActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
