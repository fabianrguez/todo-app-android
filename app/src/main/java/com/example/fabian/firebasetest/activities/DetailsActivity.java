package com.example.fabian.firebasetest.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.fabian.firebasetest.R;
import com.example.fabian.firebasetest.ToDoApp;
import com.example.fabian.firebasetest.models.ToDoItem;
import com.google.firebase.database.*;

import java.util.HashMap;
import java.util.Map;

public class DetailsActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private ToDoItem currentItem;
    private Context applicationContext;

    @Bind(R.id.date_field)
    TextView dateField;

    @Bind(R.id.done_button)
    Button doneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_view);
        currentItem = (ToDoItem) getIntent().getExtras().getSerializable("itemInfo");
        ButterKnife.bind(this);
        if(!currentItem.isCompleted()) {
            doneButton.setText(getString(R.string.done_button));
        } else {
            doneButton.setText(getString(R.string.not_done_button));
        }
        applicationContext = getApplicationContext();
        setTitle("Item Info");
        ToDoApp app = (ToDoApp) getApplicationContext();
        databaseReference = app.getItemsReference();

        dateField.setText(currentItem.getDate());
    }

    @OnClick(R.id.delete_button)
    public void deleteItemFromFirebase() {
        getItemAndDelete();
        Toast.makeText(getApplicationContext(), "Eliminando " + currentItem.getItem(), Toast.LENGTH_LONG).show();
        finishActivity();
    }

    @OnClick(R.id.done_button)
    public void updateCompletedItem() {
        getItemAndUpdate();
        Toast.makeText(getApplicationContext(), "Item '" + currentItem.getItem() + "' actualizado.", Toast.LENGTH_LONG).show();
        finishActivity();
    }

    private void getItemAndDelete() {
        Query query = databaseReference.orderByValue();
        query.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ToDoItem deleteItem = dataSnapshot.getValue(ToDoItem.class);
                if(currentItem.getRef() == deleteItem.getRef()) {
                    dataSnapshot.getRef().removeValue();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getItemAndUpdate() {
        Query query = databaseReference.orderByValue();
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ToDoItem updateItem = dataSnapshot.getValue(ToDoItem.class);
                if(currentItem.getRef() == updateItem.getRef()) {
                    Log.d("Updating", updateItem.getItem());
                    boolean completed = !currentItem.isCompleted();

                    currentItem.setCompleted(completed);
                    Map<String, Object> updates = new HashMap<>();
                    updates.put("completed", completed);
                    dataSnapshot.getRef().updateChildren(updates);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void finishActivity() {
        finish();
    }
}
