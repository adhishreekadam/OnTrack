// AssignmentAdapter.java
package com.example.myapplication;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.ImageButton;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.AssignmentViewHolder> {

    private List<AssignmentModel> assignmentList;
    private OnItemClickListener listener;

    // Constructor
    public AssignmentAdapter(List<AssignmentModel> assignmentList, OnItemClickListener listener) {
        this.assignmentList = assignmentList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AssignmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_assignment, parent, false);
        return new AssignmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AssignmentViewHolder holder, int position) {
        AssignmentModel currentAssignment = assignmentList.get(position);

        holder.assignmentNameTextView.setText(currentAssignment.getAssignmentName());
        holder.assignmentClassTextView.setText(currentAssignment.getAssociatedClass());
        // Formats the date before setting it to the TextView
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        String formattedDate = dateFormat.format(currentAssignment.getDueDate());
        holder.assignmentDueDateTextView.setText(formattedDate);

        // Sets click listeners for edit and delete buttons
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int adapterPosition = holder.getAdapterPosition();
                if (listener != null && adapterPosition != RecyclerView.NO_POSITION) {
                    listener.onEditClick(adapterPosition);
                }
            }
        });


        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int adapterPosition = holder.getAdapterPosition();
                if (listener != null && adapterPosition != RecyclerView.NO_POSITION) {
                    listener.onDeleteClick(adapterPosition);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return assignmentList.size();
    }

    static class AssignmentViewHolder extends RecyclerView.ViewHolder {
        TextView assignmentNameTextView;
        TextView assignmentClassTextView;
        TextView assignmentDueDateTextView;
        ImageButton btnEdit;
        ImageButton btnDelete;

        AssignmentViewHolder(@NonNull View itemView) {
            super(itemView);
            assignmentNameTextView = itemView.findViewById(R.id.assignmentNameTextView);
            assignmentClassTextView = itemView.findViewById(R.id.assignmentClassTextView);
            assignmentDueDateTextView = itemView.findViewById(R.id.assignmentDueDateTextView);
            btnEdit = itemView.findViewById(R.id.btnEditAssignment);
            btnDelete = itemView.findViewById(R.id.btnDeleteAssignment);
        }

    }
    // Interface for click listeners
    public interface OnItemClickListener {
        void onEditClick(int position);
        void onDeleteClick(int position);
    }
}