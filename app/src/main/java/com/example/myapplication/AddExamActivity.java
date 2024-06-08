// AddExamActivity.java
package com.example.myapplication;
import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddExamActivity extends AppCompatActivity {

    private EditText examNameEditText;
    private EditText classEditText;
    private EditText examDateTimeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exam);

        examNameEditText = findViewById(R.id.examNameEditText);
        classEditText = findViewById(R.id.classEditText);
        examDateTimeEditText = findViewById(R.id.examDateTimeEditText);

        // Button to save exam data
        Button saveExamButton = findViewById(R.id.saveExamButton);
        saveExamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveExamData();
            }
        });

        // Sets click listener for both date and time
        examDateTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateTimePickerDialog();
            }
        });
    }

    private void showDateTimePickerDialog() {
        // Initializes calendar with the current date and time
        Calendar currentDateTime = Calendar.getInstance();

        // Shows DatePickerDialog for the date
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Sets selected date to the calendar
                        currentDateTime.set(Calendar.YEAR, year);
                        currentDateTime.set(Calendar.MONTH, month);
                        currentDateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        // Shows TimePickerDialog for the time
                        showTimePickerDialog(currentDateTime);
                    }
                },
                currentDateTime.get(Calendar.YEAR),
                currentDateTime.get(Calendar.MONTH),
                currentDateTime.get(Calendar.DAY_OF_MONTH)
        );

        datePickerDialog.show();
    }

    private void showTimePickerDialog(final Calendar calendar) {
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // Sets selected time to the calendar
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);

                        // Formats the selected date and time
                        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.US);
                        String formattedDateTime = dateFormat.format(calendar.getTime());

                        // Sets the formatted date and time to the EditText
                        examDateTimeEditText.setText(formattedDateTime);
                    }
                },
                currentHour,
                currentMinute,
                false
        );

        timePickerDialog.show();
    }

    private void saveExamData() {
        // Retrieves exam details from EditText fields
        String examName = examNameEditText.getText().toString();
        String associatedClass = classEditText.getText().toString();
        String examDateTimeText = examDateTimeEditText.getText().toString();

        // Checks if any field is empty
        if (examName.isEmpty() || associatedClass.isEmpty() || examDateTimeText.isEmpty()) {
            return;
        }

        Date examDateTime = parseDateTime(examDateTimeText);

        // Checks if the date is successfully parsed
        if (examDateTime != null) {
            // Creates an Intent to send back the exam data to ExamsActivity
            Intent resultIntent = new Intent();
            resultIntent.putExtra("examName", examName);
            resultIntent.putExtra("associatedClass", associatedClass);
            resultIntent.putExtra("examDateTime", examDateTime.getTime());

            // Sets the result and finishes the activity
            setResult(RESULT_OK, resultIntent);
            finish();
        }
    }

    private Date parseDateTime(String dateTimeString) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.US);
            return dateFormat.parse(dateTimeString);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
