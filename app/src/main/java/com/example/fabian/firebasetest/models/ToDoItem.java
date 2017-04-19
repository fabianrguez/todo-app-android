package com.example.fabian.firebasetest.models;

public class ToDoItem {

    private String item;
    private boolean completed;

    public ToDoItem() {}

    public ToDoItem(String item) {
        this.item = item;
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
}
