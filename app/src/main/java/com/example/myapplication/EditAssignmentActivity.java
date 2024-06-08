//EditAssignmentActivity.java
package com.example.myapplication;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EditAssignmentActivity extends AppCompatActivity {

    private EditText editAssignmentName;
    private EditText editAssociatedClass;
    private EditText editDueDate;
    private Button saveButton;
    private AssignmentModel assignmentModel;
    private int position;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_assignment);
        // Initializes UI elements
        editAssignmentName = findViewById(R.id.editAssignmentName);
        editAssociatedClass = findViewById(R.id.editAssociatedClass);
        editDueDate = findViewById(R.id.editDueDate);
        saveButton = findViewById(R.id.saveButton);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("assignmentModel") && intent.hasExtra("position")) {
            assignmentModel = intent.getParcelableExtra("assignmentModel");
            position = intent.getIntExtra("position", -1);

            editAssignmentName.setText(assignmentModel.getAssignmentName());
            editAssociatedClass.setText(assignmentModel.getAssociatedClass());
            // Converts Date to String before setting in the EditText
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
            String formattedDate = dateFormat.format(assignmentModel.getDueDate());
            editDueDate.setText(formattedDate);
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
        String editedAssignmentName = editAssignmentName.getText().toString();
        String editedAssociatedClass = editAssociatedClass.getText().toString();

        Date editedDueDate = null;
        try {
            editedDueDate = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).parse(editDueDate.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // Updates the AssignmentModel with edited values
        assignmentModel.setAssignmentName(editedAssignmentName);
        assignmentModel.setAssociatedClass(editedAssociatedClass);
        assignmentModel.setDueDate(editedDueDate);
        // Prepares the result intent to send back to AssignmentActivity
        Intent resultIntent = new Intent();
        resultIntent.putExtra("editedAssignmentModel", assignmentModel);
        resultIntent.putExtra("position", position);
        setResult(RESULT_OK, resultIntent);

        finish();
    }
}