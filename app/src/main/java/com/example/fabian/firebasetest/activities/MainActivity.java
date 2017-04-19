package com.example.fabian.firebasetest.activities;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.fabian.firebasetest.R;

import com.example.fabian.firebasetest.adapters.ToDoItemsRecyclerAdapter;
import com.example.fabian.firebasetest.models.ToDoItem;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private FirebaseDatabase database;
    private FirebaseRecyclerAdapter adapter;
    private DatabaseReference databaseReference;

    @Bind(R.id.editTextInput)
    EditText item;

    @Bind(R.id.recycler_view_items)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("items");

        adapter = new ToDoItemsRecyclerAdapter(R.layout.row, databaseReference);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.fab)
    public void sendItemToFirebase() {
        String itemText = item.getText().toString();
        item.setText("");

        InputMethodManager inputMethodManager = (InputMethodManager)  getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

        if (!itemText.isEmpty()) {
            ToDoItem toDoItem = new ToDoItem(itemText.trim());
            databaseReference.push().setValue(toDoItem);
        }
    }
}