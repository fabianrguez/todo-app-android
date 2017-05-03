package com.example.fabian.firebasetest.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Parcelable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.example.fabian.firebasetest.R;
import com.example.fabian.firebasetest.activities.DetailsActivity;
import com.example.fabian.firebasetest.models.ToDoItem;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;

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
            //viewHolder.imgDone.setVisibility(View.VISIBLE);
            viewHolder.cardView.setCardBackgroundColor(Color.GREEN);
            System.out.println("Set green color");
        } else {
            viewHolder.cardView.setCardBackgroundColor(Color.WHITE);
            System.out.println("Set white color");
        }
    }

    class ToDoItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        private Context applicationContext;

        @Bind(R.id.txtItem)
        TextView txtItem;

        @Bind(R.id.imgDone)
        ImageView imgDone;

        @Bind(R.id.cardView)
        CardView cardView;

        public ToDoItemViewHolder(View itemView) {
            super(itemView);
            applicationContext = itemView.getContext();
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            ToDoItem currentItem = getItem(position);
            //Intent detailsView = new Intent(applicationContext, DetailsActivity.class);
            //detailsView.putExtra("itemInfo", currentItem);
            //detailsView.putExtra("itemPosition", position);
            //applicationContext.startActivity(detailsView);
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