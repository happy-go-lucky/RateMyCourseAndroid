package com.example.happi.ratemycourse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class RatingActivityEditCreateActivity extends AppCompatActivity {

    // course name
    private String _courseName;

    // Score sliders
    private SeekBar _homeworkSlider;
    private SeekBar _readingSlider;
    private SeekBar _usefullnessSlider;
    private SeekBar _stressSlider;

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

        finish();
    }

    public void onCancelButtonPressed(View v) {
        finish();
    }
}
