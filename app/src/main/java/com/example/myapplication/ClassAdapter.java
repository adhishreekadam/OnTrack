// ClassAdapter.java
package com.example.myapplication;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;


public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ClassViewHolder> {

    private List<ClassModel> classList;
    private OnItemClickListener listener;

    // Constructor
    public ClassAdapter(List<ClassModel> classList, OnItemClickListener listener) {
        this.classList = classList;
        this.listener = listener;
    }
    @NonNull
    @Override
    public ClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_class, parent, false);
        return new ClassViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassViewHolder holder, int position) {
        ClassModel currentClass = classList.get(position);

        holder.classNameTextView.setText(currentClass.getClassName());
        holder.classTimeDayTextView.setText(currentClass.getClassTimeAndDay());
        holder.classInstructorTextView.setText(currentClass.getClassInstructor());

        // Sets click listeners for add and delete buttons
        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int adapterPosition = holder.getAdapterPosition();
                if (listener != null && adapterPosition != RecyclerView.NO_POSITION) {
                    listener.onAddClick(adapterPosition);
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
        return classList.size();
    }


    static class ClassViewHolder extends RecyclerView.ViewHolder {
        TextView classNameTextView;
        TextView classTimeDayTextView;
        TextView classInstructorTextView;
        ImageButton btnAdd;
        ImageButton btnDelete;


        ClassViewHolder(@NonNull View itemView) {
            super(itemView);
            classNameTextView = itemView.findViewById(R.id.classNameTextView);
            classTimeDayTextView = itemView.findViewById(R.id.classTimeDayTextView);
            classInstructorTextView = itemView.findViewById(R.id.classInstructorTextView);
            btnAdd = itemView.findViewById(R.id.btnAdd);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }

    // Interface for click listeners
    public interface OnItemClickListener {
        void onAddClick(int position);
        void onDeleteClick(int position);
    }
}
