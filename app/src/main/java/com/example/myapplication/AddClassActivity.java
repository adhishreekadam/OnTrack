//AddClassActivity.java
package com.example.myapplication;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddClassActivity extends AppCompatActivity {

    private EditText classNameEditText;
    private EditText classTimeDayEditText;
    private EditText classInstructorEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);

        classNameEditText = findViewById(R.id.classNameEditText);
        classTimeDayEditText = findViewById(R.id.classTimeDayEditText);
        classInstructorEditText = findViewById(R.id.classInstructorEditText);

        // Button to save class data
        Button saveClassButton = findViewById(R.id.saveClassButton);
        saveClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveClassData();
            }
        });
    }

    private void saveClassData() {
        // Retrieves class details from EditText fields
        String className = classNameEditText.getText().toString();
        String classTimeDay = classTimeDayEditText.getText().toString();
        String classInstructor = classInstructorEditText.getText().toString();

        // Checks if any field is empty
        if (className.isEmpty() || classTimeDay.isEmpty() || classInstructor.isEmpty()) {
            return;
        }

        // Creates an Intent to send back the class data to ClassesActivity
        Intent resultIntent = new Intent();
        resultIntent.putExtra("className", className);
        resultIntent.putExtra("classTimeDay", classTimeDay);
        resultIntent.putExtra("classInstructor", classInstructor);

        // Sets the result and finish the activity
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}