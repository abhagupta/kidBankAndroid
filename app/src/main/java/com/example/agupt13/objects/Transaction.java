package com.example.agupt13.objects;

import java.util.Date;

/**
 * Created by agupt13 on 1/26/16.
 */
public class Transaction extends ModelObject {
    String date;
    String kidsName;
    Long value;

    public String getKidsName() {
        return kidsName;
    }

    public void setKidsName(String kidsName) {
        this.kidsName = kidsName;
    }

    String type;

    public Transaction(){

    }

    public Transaction(String date,  Long value, String type){
        this.date = date;
        this.value = value;
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
