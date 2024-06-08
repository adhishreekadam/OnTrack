//EditClassActivity.java
package com.example.myapplication;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditClassActivity extends AppCompatActivity {

    private EditText editClassName;
    private EditText editClassTimeDay;
    private EditText editClassInstructor;
    private Button saveButton;

    private ClassModel classModel;
    private int position;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_class);

        // Initializes UI elements
        editClassName = findViewById(R.id.editClassName);
        editClassTimeDay = findViewById(R.id.editClassTimeDay);
        editClassInstructor = findViewById(R.id.editClassInstructor);
        saveButton = findViewById(R.id.saveButton);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("classModel") && intent.hasExtra("position")) {
            classModel = intent.getParcelableExtra("classModel");
            position = intent.getIntExtra("position", -1);

            editClassName.setText(classModel.getClassName());
            editClassTimeDay.setText(classModel.getClassTimeAndDay());
            editClassInstructor.setText(classModel.getClassInstructor());
        }

        // Sets click listener for save button
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveChanges();
            }
        });
    }

    private void saveChanges() {
        // Retrieves edited values from UI elements
        String editedClassName = editClassName.getText().toString();
        String editedClassTimeDay = editClassTimeDay.getText().toString();
        String editedClassInstructor = editClassInstructor.getText().toString();

        // Updates the ClassModel with edited values
        classModel.setClassName(editedClassName);
        classModel.setClassTimeAndDay(editedClassTimeDay);
        classModel.setClassInstructor(editedClassInstructor);

        // Prepares result intent to send back to ClassesActivity
        Intent resultIntent = new Intent();
        resultIntent.putExtra("editedClassModel", classModel);
        resultIntent.putExtra("position", position);
        setResult(RESULT_OK, resultIntent);

        finish();
    }
}
