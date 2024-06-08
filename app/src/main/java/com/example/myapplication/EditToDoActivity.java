package com.example.myapplication;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EditToDoActivity extends AppCompatActivity {

    private EditText editTaskEditText;
    private Button saveChangesButton;

    private int position;
    private ToDoModel editedTask;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_to_do);

        // Initializes UI elements
        editTaskEditText = findViewById(R.id.editTaskEditText);
        saveChangesButton = findViewById(R.id.saveChangesButton);

        Intent intent = getIntent();
        editedTask = intent.getParcelableExtra("editedTask");

        if (editedTask == null) {
            finish();
            return;
        }

        // Sets the initial task name in the edit text
        editTaskEditText.setText(editedTask.getTask());

        saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Saves changes and returns the updated task to ToDoActivity
                saveChanges();
            }
        });
    }

    private void saveChanges() {
        // Retrieves edited value from UI element
        String updatedTaskName = editTaskEditText.getText().toString().trim();

        if (!updatedTaskName.isEmpty()) {
            // Updates the task and returns it to ToDoActivity
            editedTask.setTask(updatedTaskName);
            // Prepares the result intent to send back to ToDoActivity
            Intent resultIntent = new Intent();
            resultIntent.putExtra("editedTask", editedTask);
            resultIntent.putExtra("position", position);

            setResult(RESULT_OK, resultIntent);

            finish();
        }
    }
}

