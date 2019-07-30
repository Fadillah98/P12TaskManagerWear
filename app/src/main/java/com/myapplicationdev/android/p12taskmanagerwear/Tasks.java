package com.myapplicationdev.android.p12taskmanagerwear;

import java.io.Serializable;

public class Tasks implements Serializable {

    private int id;
    private String name;
    private String desription;

    public Tasks(int id, String name, String desription) {
        this.id = id;
        this.name = name;
        this.desription = desription;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesription() {
        return desription;
    }

    public void setDesription(String desription) {
        this.desription = desription;
    }

    @Override
    public String toString() {
        return id + " " + name + "\n" + desription;
    }
}
