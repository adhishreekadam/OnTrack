// EditExamActivity.java
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
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EditExamActivity extends AppCompatActivity {

    private EditText editExamName;
    private EditText editAssociatedClass;
    private EditText editExamDate;
    private Button saveButton;
    private ExamModel examModel;
    private int position;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_exam);

        // Initializes UI elements
        editExamName = findViewById(R.id.editExamName);
        editAssociatedClass = findViewById(R.id.editAssociatedClass);
        editExamDate = findViewById(R.id.editExamDate);
        saveButton = findViewById(R.id.saveButton);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("examModel") && intent.hasExtra("position")) {
            examModel = intent.getParcelableExtra("examModel");
            position = intent.getIntExtra("position", -1);

            editExamName.setText(examModel.getExamName());
            editAssociatedClass.setText(examModel.getAssociatedClass());

            // Converts Date to String before setting in the EditText
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.getDefault());
            String formattedDateTime = dateFormat.format(examModel.getExamDateTime().getTime());
            editExamDate.setText(formattedDateTime);
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
        String editedExamName = editExamName.getText().toString();
        String editedAssociatedClass = editAssociatedClass.getText().toString();
        String editedExamDateStr = editExamDate.getText().toString();

        Calendar editedExamDateTime = Calendar.getInstance();
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.getDefault());
            Date editedExamDate = dateFormat.parse(editedExamDateStr);
            editedExamDateTime.setTime(editedExamDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Updates the ExamModel with edited values
        examModel.setExamName(editedExamName);
        examModel.setAssociatedClass(editedAssociatedClass);
        examModel.setExamDateTime(editedExamDateTime);

        // Prepares the result intent to send back to ExamsActivity
        Intent resultIntent = new Intent();
        resultIntent.putExtra("editedExamModel", examModel);
        resultIntent.putExtra("position", position);
        setResult(RESULT_OK, resultIntent);

        finish();
    }
}

