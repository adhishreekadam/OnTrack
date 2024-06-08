// MainActivity.java
package com.example.myapplication;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.classesItem) {
                    // Navigates to ClassesActivity
                    Intent intent = new Intent(MainActivity.this, ClassesActivity.class);
                    startActivity(intent);
                } else if (item.getItemId() == R.id.assignmentsItem) {
                    // Navigates to AssignmentsActivity
                    Intent intent = new Intent(MainActivity.this, AssignmentsActivity.class);
                    startActivity(intent);
                } else if (item.getItemId() == R.id.examsItem) {
                    // Navigates to ExamsActivity
                    Intent intent = new Intent(MainActivity.this, ExamsActivity.class);
                    startActivity(intent);
                } else if(item.getItemId() == R.id.toDoItem){
                    // Navigates to ToDoActivity
                    Intent intent = new Intent(MainActivity.this, ToDoActivity.class);
                    startActivity(intent);
                }

                return true;
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottom_navigation_menu, menu);
        return true;
    }

}