package com.example.happi.ratemycourse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class CourseRatingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_rating);

        Intent intent = getIntent();
        String courseName = intent.getStringExtra("COURSE_NAME");

        // TODO: get course rating data and set textView values. If none, use defaults or display none.
        TextView courseNameTextView = findViewById(R.id.courseNameHeader);
        courseNameTextView.setText(courseName);
    }
}
