package com.example.fabian.firebasetest.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.fabian.firebasetest.R;
import com.example.fabian.firebasetest.ToDoApp;
import com.example.fabian.firebasetest.models.ToDoItem;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetailsActivity extends AppCompatActivity {

    private ToDoItem currentItem;
    private int itemPosition;

    @Bind(R.id.date_field)
    TextView dateField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_view);
        setTitle("Item Info");

        ButterKnife.bind(this);
        currentItem = (ToDoItem) getIntent().getExtras().getSerializable("itemInfo");
        itemPosition = getIntent().getExtras().getInt("itemPosition");

        dateField.setText(currentItem.getDate());

    }

    @OnClick(R.id.delete_button)
    public void deleteItemFromFirebase() {
        ToDoApp app = (ToDoApp) getApplicationContext();

    }
}
