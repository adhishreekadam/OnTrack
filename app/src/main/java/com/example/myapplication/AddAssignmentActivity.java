// AddAssignmentActivity.java
package com.example.myapplication;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddAssignmentActivity extends AppCompatActivity {

    private EditText assignmentNameEditText;
    private EditText assignmentClassNameEditText;
    private EditText assignmentDueDateEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assignment);

        assignmentNameEditText = findViewById(R.id.assignmentNameEditText);
        assignmentClassNameEditText = findViewById(R.id.assignmentClassNameEditText);
        assignmentDueDateEditText = findViewById(R.id.assignmentDueDateEditText);
        // Button to save assignment data
        Button saveAssignmentButton = findViewById(R.id.saveAssignmentButton);
        saveAssignmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAssignmentData();
            }
        });
        assignmentDueDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
    }
    private void showDatePickerDialog() {
        // Gets current date to set as the initial date in the DatePicker
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Updates the EditText field with the selected date
                    Calendar selectedCalendar = Calendar.getInstance();
                    selectedCalendar.set(selectedYear, selectedMonth, selectedDay);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
                    String formattedDate = dateFormat.format(selectedCalendar.getTime());
                    assignmentDueDateEditText.setText(formattedDate);
                },
                year, month, day
        );

        datePickerDialog.show();
    }


    private void saveAssignmentData() {
        // Retrieves assignment details from EditText fields
        String assignmentName = assignmentNameEditText.getText().toString();
        String associatedClass = assignmentClassNameEditText.getText().toString();
        String dueDate = assignmentDueDateEditText.getText().toString();

        // Checks if any field is empty
        if (assignmentName.isEmpty() || associatedClass.isEmpty() || dueDate.isEmpty()) {
            return;
        }
        Date dueDateA = parseDate(dueDate);

        // Creates an Intent to send back the assignment data to AssignmentsActivity
        Intent resultIntent = new Intent();
        resultIntent.putExtra("assignmentName", assignmentName);
        resultIntent.putExtra("associatedClass", associatedClass);
        resultIntent.putExtra("dueDate", dueDateA.getTime());

        // Sets the result and finishes the activity
        setResult(RESULT_OK, resultIntent);
        finish();
    }
    private Date parseDate(String dateString) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
            return dateFormat.parse(dateString);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}