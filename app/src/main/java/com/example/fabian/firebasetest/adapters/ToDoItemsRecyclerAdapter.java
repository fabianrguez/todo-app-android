package com.example.fabian.firebasetest.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.example.fabian.firebasetest.R;
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
        viewHolder.txtItem.setText(model.getItem());
        viewHolder.dateField.setText(model.getDate());

        if(model.isCompleted()) {
            viewHolder.doneLine.setBackgroundColor(Color.GREEN);
        } else {
            viewHolder.doneLine.setBackgroundColor(Color.LTGRAY);
        }
    }

    class ToDoItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Context applicationContext;

        @Bind(R.id.txtItem)
        TextView txtItem;

        @Bind(R.id.done_line)
        View doneLine;

        @Bind(R.id.date_field)
        TextView dateField;

        public ToDoItemViewHolder(View itemView) {
            super(itemView);
            applicationContext = itemView.getContext();
            itemView.setOnClickListener(this);
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

        @OnClick(R.id.delete_button)
        public void deleteItem() {
            AlertDialog.Builder builder = new AlertDialog.Builder(applicationContext);
            builder.setTitle("¿Estás seguro/a de eliminar el item?")
                   .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    int position = getAdapterPosition();
                    DatabaseReference reference = getRef(position);
                    reference.removeValue();
                }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
        }

        @OnClick(R.id.edit_button)
        public void editItem() {
            final int position = getAdapterPosition();
            final ToDoItem currentItem = getItem(position);

            final EditText editItem = new EditText(applicationContext);
            editItem.setSelectAllOnFocus(true);
            editItem.setText(currentItem.getItem());

            AlertDialog.Builder builder = new AlertDialog.Builder(applicationContext);
            builder
                    .setView(editItem)
                    .setTitle("Editando item")
                    .setCancelable(true)
                    .setPositiveButton("Editar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DatabaseReference reference = getRef(position);
                            currentItem.setItem(editItem.getText().toString());
                            Map<String, Object> update = new HashMap<>();
                            update.put("item", currentItem.getItem());
                            reference.updateChildren(update);
                        }
                    })
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
}