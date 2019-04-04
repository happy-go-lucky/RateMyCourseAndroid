package com.example.happi.ratemycourse;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CourseRatingActivity extends AppCompatActivity {

    private String courseName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_rating);

        Intent intent = getIntent();
        courseName = intent.getStringExtra("COURSE_NAME");

        // TODO: get course rating data and set textView values. If none, use defaults or display none.
        TextView courseNameTextView = findViewById(R.id.courseNameHeader);
        courseNameTextView.setText(courseName);
    }

    /**
     * On click button handler for the edit button.
     * Linked from the layout xml file for course rating
     * @param v
     */
    public void onEditReviewButtonPressed(View v) {
        Intent intent = new Intent(this, RatingActivityEditCreateActivity.class);
        intent.putExtra("COURSE_NAME", courseName);
        startActivity(intent);
    }
}
