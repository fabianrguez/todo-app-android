package com.example.fabian.firebasetest.activities;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.fabian.firebasetest.R;

import com.example.fabian.firebasetest.ToDoApp;
import com.example.fabian.firebasetest.adapters.ToDoItemsRecyclerAdapter;
import com.example.fabian.firebasetest.models.ToDoItem;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private FirebaseRecyclerAdapter adapter;
    private DatabaseReference databaseReference;
    private ChildEventListener toDoItemListener;

    @Bind(R.id.editTextInput)
    EditText item;

    @Bind(R.id.recycler_view_items)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        ToDoApp app = (ToDoApp) getApplicationContext();
        databaseReference = app.getItemsReference();

        adapter = new ToDoItemsRecyclerAdapter(R.layout.row, databaseReference);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.fab)
    public void sendItemToFirebase() {
        String itemText = item.getText().toString();
        item.setText("");

        //InputMethodManager inputMethodManager = (InputMethodManager)  getSystemService(Activity.INPUT_METHOD_SERVICE);
        //inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

        if (!itemText.isEmpty()) {
            ToDoItem toDoItem = new ToDoItem(itemText.trim());
            databaseReference.push().setValue(toDoItem);
        }
        toDoItemListener = databaseReference.addChildEventListener(new com.google.firebase.database.ChildEventListener() {

            @Override
            public void onChildAdded(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {
                recyclerView.scrollToPosition(adapter.getItemCount() - 1);
            }

            @Override
            public void onChildChanged(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(com.google.firebase.database.DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(com.google.firebase.database.DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}