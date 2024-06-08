package com.example.myapplication;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.PopupMenu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ClassesActivity extends AppCompatActivity implements ClassAdapter.OnItemClickListener {
    private ClassAdapter classAdapter;
    private static final int EDIT_REQUEST_CODE = 123;
    private ArrayList<ClassModel> classData;
    public static final String SHARED_PREFS = "onTrackSharedPrefs";
    public static final String KEY = "classData";

    //On Create
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classes);
        getData();

        // RecyclerView setup
        RecyclerView recyclerView = findViewById(R.id.classesRecyclerView);
        classAdapter = new ClassAdapter(classData, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(classAdapter);

        FloatingActionButton fabAddExam = findViewById(R.id.addClassButton);
        fabAddExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAddClassPage();
            }
        });

        ImageButton backButton = findViewById(R.id.backButtonClass);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassesActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        // Filter Button
        ImageButton btnFilterExams = findViewById(R.id.btnFilterClass);
        btnFilterExams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleFilterItemClick();
            }
        });


    }

    //goToAddClassPage
    private void goToAddClassPage () {
        Intent intent = new Intent(this, AddClassActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
                // Handles the result from AddClassActivity
                String className = data.getStringExtra("className");
                String classTimeDay = data.getStringExtra("classTimeDay");
                String classInstructor = data.getStringExtra("classInstructor");

                // Creates a new ClassModel object and add it to the list
                ClassModel newClass = new ClassModel(className, classTimeDay, classInstructor);
                classData.add(newClass);
                saveData(classData);

                // Notifies the adapter about the data change
                classAdapter.notifyDataSetChanged();
            }
        if (requestCode == EDIT_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            // Handles the result from EditClassActivity
            ClassModel editedClassModel = data.getParcelableExtra("editedClassModel");
            int position = data.getIntExtra("position", -1);


            if (position != -1 && editedClassModel != null) {
                // Updates the list with the edited class model
                classData.set(position, editedClassModel);
                saveData(classData);
                classAdapter.notifyItemChanged(position);
            }
        }


    }
    private void handleFilterItemClick() {

            Collections.sort(classData, new Comparator<ClassModel>() {
                @Override
                public int compare(ClassModel class1, ClassModel class2) {
                    return class1.getClassName().compareTo(class2.getClassName());
                }
            });
            classAdapter.notifyDataSetChanged();
    }

    //OnAddClick
    @Override
    public void onAddClick(int position) {
        // Handles edit button click for the item at the given position
        ClassModel clickedClass = classData.get(position);

        // Launches the EditClassActivity for editing
        Intent intent = new Intent(this, EditClassActivity.class);
        intent.putExtra("classModel", clickedClass);
        intent.putExtra("position", position);
        startActivityForResult(intent, EDIT_REQUEST_CODE);
    }
    //OnDelete
    @Override
    public void onDeleteClick(int position) {
        String deletedClassName = classData.get(position).getClassName();
        classData.remove(position);
        saveData(classData);
        classAdapter.notifyItemRemoved(position);

        String toastMessage = "Class '" + deletedClassName + "' deleted";
        showToast(toastMessage);
    }
    // Helper method to show a toast message
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    private void saveData(ArrayList<ClassModel> classData) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        Gson gson = new Gson();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = "";
        if(classData.size() > 0) {
            json = gson.toJson(classData);
        }
        editor.putString(KEY, json);
        editor.apply();
    }
    private void getData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String classesJsonString = sharedPreferences.getString(KEY, "");
        classData = new ArrayList<>();
        if(!classesJsonString.equals("")) {

            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<ClassModel>>(){}.getType();
            classData = gson.fromJson(classesJsonString, listType);
        }
    }

}