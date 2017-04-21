package com.example.fabian.firebasetest;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ToDoApp extends Application {

    private String ITEMS_CHILD_NAME = "items";
    private DatabaseReference itemsReference;

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.setPersistenceEnabled(true);
        itemsReference = database.getReference(ITEMS_CHILD_NAME);
    }

    public DatabaseReference getItemsReference() {
        return itemsReference;
    }
}
