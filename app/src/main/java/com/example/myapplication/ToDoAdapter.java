//ToDoAdapter.java
package com.example.myapplication;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ToDoViewHolder> {

    public interface OnItemClickListener {
        void onEditClick(int position);

        void onDeleteClick(int position);
    }

    private List<ToDoModel> toDoList;
    private OnItemClickListener listener;

    // Constructor
    public ToDoAdapter(ArrayList<ToDoModel> toDoList, OnItemClickListener listener) {
        this.toDoList = toDoList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ToDoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_to_do, parent, false);
        return new ToDoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoViewHolder holder, int position) {
        ToDoModel currentTask = toDoList.get(holder.getAdapterPosition());

        holder.taskTextView.setText(currentTask.getTask());
        holder.taskCheckbox.setChecked(currentTask.isChecked());

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onEditClick(holder.getAdapterPosition());
                }
            }
        });
        // Sets click listeners for edit and delete buttons
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onDeleteClick(holder.getAdapterPosition());
                }
            }
        });

        holder.taskCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                currentTask.setChecked(isChecked);
                updateTextViewStyle(holder.taskTextView, isChecked);
            }
        });
    }

    private void updateTextViewStyle(TextView textView, boolean isChecked) {
        if (isChecked) {
            textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            textView.setPaintFlags(textView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }
    }

    @Override
    public int getItemCount() {
        return toDoList.size();
    }

    static class ToDoViewHolder extends RecyclerView.ViewHolder {
        CheckBox taskCheckbox;
        TextView taskTextView;
        ImageButton btnEdit;
        ImageButton btnDelete;

        ToDoViewHolder(@NonNull View view) {
            super(view);
            taskCheckbox = view.findViewById(R.id.todoCheckbox);
            taskTextView = view.findViewById(R.id.taskEditText);
            btnEdit = view.findViewById(R.id.btnEditToDo);
            btnDelete = view.findViewById(R.id.btnDeleteToDo);
        }
    }
}

