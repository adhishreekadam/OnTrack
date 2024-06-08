// ExamAdapter.java
package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ExamAdapter extends RecyclerView.Adapter<ExamAdapter.ExamViewHolder> {

    private List<ExamModel> examList;
    private OnItemClickListener listener;

    // Constructor
    public ExamAdapter(List<ExamModel> examList, OnItemClickListener listener) {
        this.examList = examList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ExamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_exam, parent, false);
        return new ExamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExamViewHolder holder, int position) {
        ExamModel currentExam = examList.get(position);

        holder.examNameTextView.setText(currentExam.getExamName());
        holder.examClassTextView.setText(currentExam.getAssociatedClass());

        // Formats the date and time before setting it to the TextView
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.getDefault());
        String formattedDateTime = dateFormat.format(currentExam.getExamDateTime().getTime());
        holder.examDateTextView.setText(formattedDateTime);

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
        return examList.size();
    }

    static class ExamViewHolder extends RecyclerView.ViewHolder {
        TextView examNameTextView;
        TextView examClassTextView;
        TextView examDateTextView;
        ImageButton btnEdit;
        ImageButton btnDelete;

        ExamViewHolder(@NonNull View itemView) {
            super(itemView);
            examNameTextView = itemView.findViewById(R.id.examNameTextView);
            examClassTextView = itemView.findViewById(R.id.examClassTextView);
            examDateTextView = itemView.findViewById(R.id.examDateTextView);
            btnEdit = itemView.findViewById(R.id.btnEditExam);
            btnDelete = itemView.findViewById(R.id.btnDeleteExam);
        }
    }

    // Interface for click listeners
    public interface OnItemClickListener {
        void onEditClick(int position);
        void onDeleteClick(int position);
    }
}
