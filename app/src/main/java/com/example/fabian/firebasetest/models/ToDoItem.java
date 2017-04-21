package com.example.fabian.firebasetest.models;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("serial")
public class ToDoItem implements Serializable{

    private String item;
    private boolean completed;
    private String date;
    private long ref;

    public ToDoItem() {}

    public ToDoItem(String item) {
        this.item = item;
        this.date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
        this.ref = System.currentTimeMillis();
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getRef() {
        return ref;
    }

    public void setRef(long ref) {
        this.ref = ref;
    }
}
