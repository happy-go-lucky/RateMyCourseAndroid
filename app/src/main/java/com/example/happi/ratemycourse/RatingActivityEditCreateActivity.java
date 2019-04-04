package com.example.happi.ratemycourse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class RatingActivityEditCreateActivity extends AppCompatActivity {

    private String courseName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_edit_create);

        courseName = getIntent().getStringExtra("COURSE_NAME");

        TextView courseNameTextView = findViewById(R.id.courseNameHeader);

        courseNameTextView.setText(courseName);

        // TODO add default values to the edit fields if user has an existing review of this course
    }
}
