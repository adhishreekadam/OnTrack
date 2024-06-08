package com.example.myapplication;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

import java.util.ArrayList;

public class ToDoActivity extends AppCompatActivity implements ToDoAdapter.OnItemClickListener {

    private ToDoAdapter toDoAdapter;
    private ArrayList<ToDoModel> taskList;
    public static final String SHARED_PREFS = "onTrackSharedPrefs";
    public static final String KEY = "taskList";

    private static final int EDIT_TODO_REQUEST_CODE = 3;
    private static final int ADD_TO_DO_REQUEST_CODE = 1;


    //On Create
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);
        getData();


        RecyclerView recyclerView = findViewById(R.id.toDoRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        toDoAdapter = new ToDoAdapter(taskList, this);
        recyclerView.setAdapter(toDoAdapter);

        FloatingActionButton addToDo = findViewById(R.id.addToDoButton);
        addToDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToAddToDoPage();
            }
        });

        ImageButton backButton = findViewById(R.id.backButtonToDo);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ToDoActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


    //goToAddToDoPage
    private void goToAddToDoPage() {
        Intent intent = new Intent(this, AddToDoActivity.class);
        startActivityForResult(intent, ADD_TO_DO_REQUEST_CODE);
    }

    //onActivityResult
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_TO_DO_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            String task = data.getStringExtra("taskName");

            if (task != null) {
                ToDoModel newTask = new ToDoModel(task);
                taskList.add(newTask);
                saveData(taskList);
                toDoAdapter.notifyDataSetChanged();
            }
        } else if (requestCode == EDIT_TODO_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            // Handles result from EditToDoActivity
            ToDoModel editedTask = data.getParcelableExtra("editedTask");
            int position = data.getIntExtra("position", -1);

            if (editedTask != null && position != -1) {
            // Updates the existing task in the list
                taskList.set(position, editedTask);
                saveData(taskList);
                toDoAdapter.notifyItemChanged(position);
            }
        }
    }

    //On Edit
    @Override
    public void onEditClick(int position) {
        // Handles edit button click for the task at the given position
        ToDoModel editedTask = taskList.get(position);

        // Launches the EditToDoActivity for editing
        Intent editIntent = new Intent(this, EditToDoActivity.class);
        editIntent.putExtra("editedTask", editedTask);
        editIntent.putExtra("position", position);
        startActivityForResult(editIntent, EDIT_TODO_REQUEST_CODE);
    }

    //On Delete
    @Override
    public void onDeleteClick(int position) {

        taskList.remove(position);
        saveData(taskList);
        toDoAdapter.notifyItemRemoved(position);

        String toastMessage = "To-Do deleted";
        showToast(toastMessage);
    }
    // Helper method to show a toast message
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    private void saveData(ArrayList<ToDoModel> taskList) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        Gson gson = new Gson();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String json = "";
        if(taskList.size() > 0) {
            json = gson.toJson(taskList);
        }
        editor.putString(KEY, json);
        editor.apply();
    }


    private void getData() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String todoJsonString = sharedPreferences.getString(KEY, "");
        taskList = new ArrayList<>();
        if(!todoJsonString.equals("")) {
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<ToDoModel>>(){}.getType();
            taskList = gson.fromJson(todoJsonString, listType);
        }


    }

}
