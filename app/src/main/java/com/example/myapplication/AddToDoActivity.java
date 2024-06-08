// AddToDoActivity.java
package com.example.myapplication;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;


public class AddToDoActivity extends AppCompatActivity {
    private EditText taskEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_do);
        taskEditText = findViewById(R.id.taskEditText);
        // Button to save to-do data
        Button saveToDoButton = findViewById(R.id.saveToDoButton);
        saveToDoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAssignmentData();
            }
        });
    }

    private void saveAssignmentData() {
        // Retrieves to-do details from EditText fields
        String taskName = taskEditText.getText().toString();

        if (taskName.isEmpty()) {
            return;
        }
        // Creates an Intent to send back the to-do data to ToDoActivity
        Intent resultIntent = new Intent();
        resultIntent.putExtra("taskName", taskName);

        // Sets the result and finishes the activity
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}
