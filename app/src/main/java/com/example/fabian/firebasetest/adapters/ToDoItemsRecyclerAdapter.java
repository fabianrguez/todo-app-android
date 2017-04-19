package com.example.fabian.firebasetest.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.example.fabian.firebasetest.R;
import com.example.fabian.firebasetest.models.ToDoItem;
import com.firebase.client.Firebase;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.*;

import java.util.HashMap;
import java.util.Map;

public class ToDoItemsRecyclerAdapter extends FirebaseRecyclerAdapter<ToDoItem, ToDoItemsRecyclerAdapter.ToDoItemViewHolder> {

    public ToDoItemsRecyclerAdapter(int modelLayout, DatabaseReference ref) {
        super(ToDoItem.class, modelLayout, ToDoItemViewHolder.class, ref);
    }

    @Override
    public ToDoItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewGroup view = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(mModelLayout, parent, false);
        return new ToDoItemViewHolder(view);
    }

    @Override
    protected void populateViewHolder(ToDoItemViewHolder viewHolder, ToDoItem model, int position) {
        String itemDescription = model.getItem();

        viewHolder.txtItem.setText(itemDescription);

        if(model.isCompleted()) {
            viewHolder.imgDone.setVisibility(View.VISIBLE);
        } else {
            viewHolder.imgDone.setVisibility(View.INVISIBLE);
        }
    }

    class ToDoItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        @Bind(R.id.txtItem)
        TextView txtItem;

        @Bind(R.id.imgDone)
        ImageView imgDone;

        public ToDoItemViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            ToDoItem currentItem = getItem(position);
            DatabaseReference reference = getRef(position);
            boolean completed = !currentItem.isCompleted();

            currentItem.setCompleted(completed);
            Map<String, Object> updates = new HashMap<>();
            updates.put("completed", completed);
            reference.updateChildren(updates);
        }

        @Override
        public boolean onLongClick(View view) {
            int position = getAdapterPosition();
            DatabaseReference reference = getRef(position);
            reference.removeValue();
            return true;
        }
    }
}