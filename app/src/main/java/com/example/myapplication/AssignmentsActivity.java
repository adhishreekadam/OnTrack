// AssignmentsActivity.java
package com.example.myapplication;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class AssignmentsActivity extends AppCompatActivity implements AssignmentAdapter.OnItemClickListener {

    private AssignmentAdapter assignmentAdapter;
    private ArrayList<AssignmentModel> assignmentData;
    public static final String SHARED_PREFS = "onTrackSharedPrefs";
    public static final String KEY = "assignmentData";
    private static final int ADD_ASSIGNMENT_REQUEST_CODE = 1;
    private static final int EDIT_ASSIGNMENT_REQUEST_CODE = 2;

    //On Create
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignments);
        getData();

        // RecyclerView to display assignments
        RecyclerView recyclerView = findViewById(R.id.assignmentsRecyclerView);
        assignmentAdapter = new AssignmentAdapter(assignmentData, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(assignmentAdapter);

        FloatingActionButton fabAddExam = findViewById(R.id.addAssignmentsButton);
        fabAddExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAddAssignmentPage();
            }
        });

        ImageButton backButton = findViewById(R.id.backButtonAssignment);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AssignmentsActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        // Filter Button
        ImageButton btnFilterExams = findViewById(R.id.btnFilterAssignments);
        btnFilterExams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFilterMenu(v);
            }
        });
    }
    private void showFilterMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(this, v);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.menu_filter, popupMenu.getMenu());


        // Sets click listener for the menu items
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                handleFilterItemClick(item);
                return true;
            }
        });

        popupMenu.show();
    }
    private void handleFilterItemClick(MenuItem item) {
        if (item.getItemId() == R.id.menuFilterAlphabetical) {
            // Handles alphabetical filter
            Collections.sort(assignmentData, new Comparator<AssignmentModel>() {
                @Override
                public int compare(AssignmentModel assignment1, AssignmentModel assignment2) {
                    return assignment1.getAssignmentName().compareTo(assignment2.getAssignmentName());
                }
            });
            assignmentAdapter.notifyDataSetChanged();
        } else if (item.getItemId() == R.id.menuFilterByDate) {
            // Handles date filter
            Collections.sort(assignmentData, new Comparator<AssignmentModel>() {
                @Override
               public int compare(AssignmentModel assignment1, AssignmentModel assignment2) {
                    return assignment1.getDueDate().compareTo(assignment2.getDueDate());
                }
            });
            assignmentAdapter.notifyDataSetChanged();
        }
    }

    //goToAddAssignmentPage
    private void goToAddAssignmentPage() {
        Intent intent = new Intent(this, AddAssignmentActivity.class);
        startActivityForResult(intent, ADD_ASSIGNMENT_REQUEST_CODE);
    }

    //onActivityResult
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_ASSIGNMENT_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            String assignmentName = data.getStringExtra("assignmentName");
            String associatedClass = data.getStringExtra("associatedClass");

            // Retrieves the date as a long value from the Intent
            long dateMillis = data.getLongExtra("dueDate", -1);

            if (assignmentName != null && associatedClass != null && dateMillis != -1) {
                // Converts the long value to a Date
                Date dueDate = new Date(dateMillis);

                AssignmentModel newAssignment = new AssignmentModel(assignmentName, associatedClass, dueDate);
                assignmentData.add(newAssignment);
                saveData(assignmentData);

                assignmentAdapter.notifyDataSetChanged();
            }
        } else if (requestCode == EDIT_ASSIGNMENT_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            // Handles the result from the EditAssignmentActivity
            AssignmentModel editedAssignment = data.getParcelableExtra("editedAssignmentModel");
            int position = data.getIntExtra("position", -1);

            if (editedAssignment != null && position != -1) {
                // Updates the existing assignment in the list
                assignmentData.set(position, editedAssignment);
                saveData(assignmentData);
                assignmentAdapter.notifyItemChanged(position);
            }
        }
    }
    //on edit
    @Override
    public void onEditClick(int position) {
        // Handles edit button click for the assignments at the given position
        AssignmentModel clickedAssignment = assignmentData.get(position);

        // Launches the EditAssignmentActivity for editing
        Intent editIntent = new Intent(this, EditAssignmentActivity.class);
        editIntent.putExtra("assignmentModel", clickedAssignment);
        editIntent.putExtra("position", position);
        startActivityForResult(editIntent, EDIT_ASSIGNMENT_REQUEST_CODE);
    }

    //on delete
    @Override
    public void onDeleteClick(int position) {
        // Retrieves the deleted assignment name for the toast message
        String deletedAssignmentName = assignmentData.get(position).getAssignmentName();
        assignmentData.remove(position);
        saveData(assignmentData);
        assignmentAdapter.notifyItemRemoved(position);

        // Shows a toast message indicating that the assignment is deleted
        String toastMessage = "Assignment '" + deletedAssignmentName + "' deleted";
        showToast(toastMessage);
    }
    // Helper method to show a toast message
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    private void saveData(ArrayList<AssignmentModel> assignmentData) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        Gson gson = new Gson();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = "";
        if (assignmentData.size() > 0) {
            json = gson.toJson(assignmentData);
        }
        editor.putString(KEY, json);
        editor.apply();
    }


    private void getData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String assignmentJsonString = sharedPreferences.getString(KEY,"");
        assignmentData = new ArrayList<>();
        if(!assignmentJsonString.equals("")) {

            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<AssignmentModel>>() {}.getType();
            assignmentData = gson.fromJson(assignmentJsonString, listType);
        }
    }


}