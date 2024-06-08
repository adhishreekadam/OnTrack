// ExamsActivity.java
package com.example.myapplication;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import android.widget.Toast;

import android.widget.ImageButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;



public class ExamsActivity extends AppCompatActivity implements ExamAdapter.OnItemClickListener{

    private ExamAdapter examAdapter;
    private static final int EDIT_REQUEST_CODE = 2;
    private ArrayList<ExamModel> examData;
    public static final String SHARED_PREFS = "onTrackSharedPrefs";
    public static final String KEY = "examData";


    //On Create
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exams);
        getData();

        RecyclerView recyclerView = findViewById(R.id.examsRecyclerView);
        examAdapter = new ExamAdapter(examData, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(examAdapter);

        FloatingActionButton fabAddExam = findViewById(R.id.fabAddExam);
        fabAddExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAddExamPage();
            }
        });

        ImageButton backButton = findViewById(R.id.backButtonExams);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handles button click (e.g., go back to the main page)
                Intent intent = new Intent(ExamsActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    //goToAddExamPage
    private void goToAddExamPage() {
        Intent intent = new Intent(this, AddExamActivity.class);
        startActivityForResult(intent, 1);
    }

    //onActivityResult
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == 1) {
                String examName = data.getStringExtra("examName");
                String associatedClass = data.getStringExtra("associatedClass");
                // Retrieves the date as a long value from the Intent
                long dateMillis = data.getLongExtra("examDateTime", -1);

                if (examName != null && associatedClass != null && dateMillis != -1) {
                    // Converts the long value to a Date
                    Calendar examDateTime = Calendar.getInstance();
                    examDateTime.setTimeInMillis(dateMillis);

                    ExamModel newExam = new ExamModel(examName, associatedClass, examDateTime);
                    examData.add(newExam);
                    //Sorts the examData based on date
                    Collections.sort(examData, new Comparator<ExamModel>() {
                        @Override
                        public int compare(ExamModel exam1, ExamModel exam2) {
                           return exam1.getExamDateTime().compareTo(exam2.getExamDateTime());
                       }
                    });
                    saveData(examData);
                    examAdapter.notifyDataSetChanged();
                }
            } else if (requestCode == EDIT_REQUEST_CODE) {
                // Handles result from EditExamActivity
                ExamModel editedExam = data.getParcelableExtra("editedExamModel");
                int position = data.getIntExtra("position", -1);

                if (editedExam != null && position != -1) {
                    // Updates the existing exam in the list
                    examData.set(position, editedExam);
                    saveData(examData);
                    examAdapter.notifyItemChanged(position);
                }
            }
        }
    }
    //On Edit
    @Override
    public void onEditClick(int position) {
        // Handles edit button click for the exam at the given position
        ExamModel clickedExam = examData.get(position);
        // Launches the EditExamActivity for editing
        Intent editIntent = new Intent(this, EditExamActivity.class);
        editIntent.putExtra("examModel", clickedExam);
        editIntent.putExtra("position", position);
        startActivityForResult(editIntent, EDIT_REQUEST_CODE);
    }

    //On Delete
    @Override
    public void onDeleteClick(int position) {
        String deletedExamName = examData.get(position).getExamName();
        examData.remove(position);
        saveData(examData);
        examAdapter.notifyItemRemoved(position);

        // Shows a toast message indicating that the exam is deleted
        String toastMessage = "Exam '" + deletedExamName + "' deleted";
        showToast(toastMessage);
    }

    // Helper method to show a toast message
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    private void saveData(ArrayList<ExamModel> classData) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        Gson gson = new Gson();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = "";
        if(classData.size() > 0) {
            json = gson.toJson(examData);
        }
        editor.putString(KEY, json);
        editor.apply();
    }
    private void getData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String examJsonString= sharedPreferences.getString(KEY, "");
        examData = new ArrayList<>();
        if(!examJsonString.equals("")) {
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<ExamModel>>() {}.getType();
            examData = gson.fromJson(examJsonString, listType);
        }


    }

}