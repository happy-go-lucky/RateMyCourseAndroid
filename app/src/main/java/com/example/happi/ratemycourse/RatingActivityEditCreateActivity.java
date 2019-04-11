package com.example.happi.ratemycourse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.happi.ratemycourse.database.CourseDataHandler;
import com.example.happi.ratemycourse.database.CourseDataModel;
import com.example.happi.ratemycourse.database.RatingDataHandler;
import com.example.happi.ratemycourse.database.RatingDataModel;
import com.example.happi.ratemycourse.database.UserDataHandler;
import com.example.happi.ratemycourse.database.UserDataModel;
import com.example.happi.ratemycourse.util.TextEncoder;

import static java.lang.Integer.parseInt;

public class RatingActivityEditCreateActivity extends AppCompatActivity {

    // course name
    private String _courseName;

    // Score sliders
    private SeekBar _homeworkSlider;
    private SeekBar _readingSlider;
    private SeekBar _usefullnessSlider;
    private SeekBar _stressSlider;

    private CourseDataHandler _courseDataHandler;
    private RatingDataHandler _ratingDataHandler;
    private UserDataHandler _userDataHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_edit_create);

        // set course name header
        _courseName = getIntent().getStringExtra("COURSE_NAME");
        TextView courseNameTextView = findViewById(R.id.courseNameHeader);
        courseNameTextView.setText(_courseName);

        // setup slider listener
        _homeworkSlider = findViewById(R.id.homeworkSlider);
        setupSeekbarListener(_homeworkSlider, (TextView)findViewById(R.id.homeworkScoreDisplay));

        _readingSlider = findViewById(R.id.readingSlider);
        setupSeekbarListener(_readingSlider, (TextView)findViewById(R.id.readingScoreDisplay));

        _usefullnessSlider = findViewById(R.id.usefulnessSlider);
        setupSeekbarListener(_usefullnessSlider, (TextView)findViewById(R.id.usefulnessScoreDisplay));

        _stressSlider = findViewById(R.id.stressSlider);
        setupSeekbarListener(_stressSlider, (TextView)findViewById(R.id.stressScoreDisplay));

        // TODO add default values to the edit fields if user has an existing review of this course
    }

    /**
     * Sets up the listener for the given Seekbar
     * @param bar The Seekbar view object to apply the listener to
     * @param seekBarDisplay The TextView view object to show the progress of the Seekbar
     */
    private void setupSeekbarListener(final SeekBar bar, final TextView seekBarDisplay) {
        bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress = (int)progress;

                if (seekBarDisplay != null) {
                    seekBarDisplay.setText("" + progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
    }

    public void onOKButtonPressed(View v) {
        int homeworkScore = _homeworkSlider.getProgress();
        int readingScore = _readingSlider.getProgress();
        int usefullnessScore = _usefullnessSlider.getProgress();
        int stressScore = _stressSlider.getProgress();

        // TODO update or insert scores
        RatingDataModel newRating = new RatingDataModel(homeworkScore,
                readingScore,
                usefullnessScore,
                stressScore
        );

        // TODO refactor into courseDataHandler
//        View view = new View(this);
//        _courseDataHandler = new CourseDataHandler(view.getContext());

        String[] split = _courseName.split("(?=(....)+$)");
//        String courseCode = split[0];
//        CourseDataModel.CourseCode courseCode = CourseDataModel.CourseCode.valueOf(split[0]);
//        int courseNumber = parseInt(split[1]);
        CourseDataModel currentCourse = _courseDataHandler.getCourse(CourseDataModel.CourseCode.COMP, 8051);

        // TODO get user data from login
        TextEncoder passEncode = new TextEncoder();
        UserDataModel newUser = new UserDataModel(
                "stillusinghotmail@yahoo.com",
                passEncode.getEncodedText("password"),
                "Stan",
                "Marsh",
                "Kirk"
        );

        _ratingDataHandler.addRating(newRating, currentCourse, newUser);

        finish();
    }

    public void onCancelButtonPressed(View v) {
        finish();
    }
}
