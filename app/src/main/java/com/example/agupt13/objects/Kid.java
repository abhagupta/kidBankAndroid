package com.example.agupt13.objects;

import java.util.Date;

/**
 * Created by agupt13 on 1/22/16.
 */
public class Kid extends ModelObject{

    public String name;
    public String rewards;

    public Kid(String name, String rewards){
        this.name = name;
        this.rewards = rewards;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRewards(String rewards) {
        this.rewards = rewards;
    }

    public String getRewards() {
        return rewards;
    }

}
