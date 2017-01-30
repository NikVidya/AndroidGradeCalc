package com.example.nick.a381week3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String DEBUG_TAG = "CourseGrades";

    // Declares each key string (for onSaveInstanceState), the value and the widget variable

    private static final String ASSIGNMENTS = "ASSIGNMENTS";
    private float assgn = 0;
    private EditText assgnEditText;

    private static final String PARTICIPATION = "PARTICIPATION";
    private float particip = 0;
    private EditText participEditText;

    private static final String PROJECT = "PROJECT";
    private float project = 0;
    private EditText projectEditText;

    private static final String QUIZZES = "QUIZZES";
    private float quiz = 0;
    private EditText quizEditText;

    private float exam = 0;
    private EditText examEditText;
    private static final String EXAMBAR = "EXAMBAR";
    private SeekBar examSeekBar;

    private TextView finalMarkTextView;
    private Button resetButton;

    // Listens to the seekBar progress and updates the exam mark on change
    private SeekBar.OnSeekBarChangeListener examSeekBarListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            exam = seekBar.getProgress();
            Log.i(DEBUG_TAG, "exam value "+exam);
            updateExam();
            updateMark();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };
    // Listens to any text change and updates the mark on change
    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        // Checks which editText is in focus and tries to do a live update of the final mark
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(assgnEditText.isFocused()){
                try {
                    assgn = Float.parseFloat(s.toString());
                }
                catch (NumberFormatException e){
                    assgn = 0;
                }
            }
            if(participEditText.isFocused()){
                try {
                    particip = Float.parseFloat(s.toString());
                }
                catch (NumberFormatException e){
                    particip = 0;
                }
            }
            if(projectEditText.isFocused()){
                try {
                    project = Float.parseFloat(s.toString());
                }
                catch (NumberFormatException e){
                    project = 0;
                }
            }
            if(quizEditText.isFocused()){
                try {
                    quiz = Float.parseFloat(s.toString());
                }
                catch (NumberFormatException e){
                    quiz = 0;
                }
            }
            updateMark();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    // Listens to the reset button and resets all values to default upon click
    private View.OnClickListener l = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            assgn = 0;
            assgnEditText.setText(Float.toString(assgn));
            particip = 0;
            participEditText.setText(Float.toString(particip));
            project = 0;
            projectEditText.setText(Float.toString(project));
            quiz = 0;
            quizEditText.setText(Float.toString(quiz));
            examSeekBar.setProgress(80);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Assigns the XML widgets to corresponding variables and adds textChangedListener
        assgnEditText = (EditText) findViewById(R.id.editTextAssignments);
        assgnEditText.addTextChangedListener(watcher);

        participEditText = (EditText) findViewById(R.id.editTextParticipation);
        participEditText.addTextChangedListener(watcher);

        projectEditText = (EditText) findViewById(R.id.editTextProject);
        projectEditText.addTextChangedListener(watcher);

        quizEditText = (EditText) findViewById(R.id.editTextQuizzes);
        quizEditText.addTextChangedListener(watcher);

        // Doesn't use a textChangedListener since it is changed with the seekBar
        examEditText = (EditText) findViewById(R.id.editTextExam);
        examSeekBar = (SeekBar) findViewById(R.id.seekBarExam);
        examSeekBar.setOnSeekBarChangeListener(examSeekBarListener);

        finalMarkTextView = (TextView) findViewById(R.id.textViewFinal);

        resetButton = (Button) findViewById(R.id.clearButton);
        resetButton.setOnClickListener(l);
    }

    // Saves current values upon activity changes
    protected void onSaveInstanceState(Bundle outState){
        outState.putFloat(ASSIGNMENTS, assgn);
        outState.putFloat(PROJECT, project);
        outState.putFloat(QUIZZES, quiz);
        outState.putFloat(PARTICIPATION, particip);
        outState.putInt(EXAMBAR, examSeekBar.getProgress());
    }
    // Grabs the saved values and reassigns them to the variables
    protected void onRestoreInstanceState(Bundle inState){
        assgn = inState.getFloat(ASSIGNMENTS);
        project = inState.getFloat(PROJECT);
        quiz = inState.getFloat(QUIZZES);
        particip = inState.getFloat(PARTICIPATION);
        examSeekBar.setProgress(inState.getInt(EXAMBAR));

        // Reassign editText values
        assgnEditText.setText(Float.toString(assgn));
        participEditText.setText(Float.toString(particip));
        projectEditText.setText(Float.toString(project));
        quizEditText.setText(Float.toString(quiz));

        // Set exam and final mark values after restoring
        updateExam();
        updateMark();
    }

    // Sets the exam widget text to the value of exam (which was set by seekBar)
    private void updateExam(){
        examEditText.setText(String.format("%.1f", exam));
    }

    // Uses the marking scheme to calculate the final mark and set the widget text to it
    private void updateMark(){
        // Can't get bonus marks
        if (quiz > 100) {
            quiz = 100;
        }
        if (assgn > 100) {
            assgn = 100;
        }
        if (particip > 100) {
            particip = 100;
        }
        if (project > 100) {
            project = 100;
        }

        float finalMark = assgn*15/100 + particip*15/100 + project*20/100 + quiz*20/100 + exam*30/100;
        finalMarkTextView.setText(String.format("%.02f", finalMark));
    }
}
