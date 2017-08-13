package com.example.mike.geoquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {


    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;             // next question btn
    private ImageButton mPreviousButton;         // previous question btn
    private TextView mQuestionTextView;     // holds the question text
    private int mCurrentIndex = 0;

    // array of questions to ask
    private Question[]  mQuestionBank = new Question[] {
        new Question(R.string.question_australia,true),
        new Question(R.string.question_oceans,true),
        new Question(R.string.question_mideast,false),
            new Question(R.string.question_africa,false),
            new Question(R.string.question_americas,true),
            new Question(R.string.question_asia,true)

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_quiz);

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        // get the first question's id
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        // set the text based on the ID
        mQuestionTextView.setText(question);

        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mPreviousButton = (ImageButton) findViewById(R.id.previous_button);

        mTrueButton = (Button) findViewById(R.id.true_button);
        mFalseButton = (Button) findViewById(R.id.false_button);

        mQuestionTextView = (TextView) findViewById((R.id.question_text_view)) ;


        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                checkAnswer(true);
            /*
               Toast t =  Toast.makeText(QuizActivity.this,
                        R.string.correct_toast,Toast.LENGTH_SHORT);
                t.setGravity(Gravity.TOP,t.getXOffset(),t.getYOffset());
                t.show();
                */
            }
        });

        // listener for question text
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex+1) % mQuestionBank.length;
                updateQuestion();
            }

        });

        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);

/*
                Toast t = Toast.makeText(QuizActivity.this,
                        R.string.incorrect_toast,Toast.LENGTH_SHORT);
                t.setGravity(Gravity.TOP,t.getXOffset(),t.getYOffset());
                t.show();
                */

            }
        });

        // listener for next button
        mNextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                getNextQuestionIndex(1);
                //mCurrentIndex = (mCurrentIndex+1) % mQuestionBank.length;
                updateQuestion();

            }
        });

        // listner for previous button
        mPreviousButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                getNextQuestionIndex(-1);
                updateQuestion();
            }
        });

    }

    // updates the question text
    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        // set the text
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue) {
       boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResId = 0;

        if(userPressedTrue == answerIsTrue) {
            messageResId = R.string.correct_toast;
        } else {
            messageResId = R.string.incorrect_toast;
        }

        Toast.makeText(this,messageResId,Toast.LENGTH_SHORT).show();
    }

    private void getNextQuestionIndex(int val) {
        // if previous button exceeds lower bounds, go back to last question
        mCurrentIndex = (mCurrentIndex+val <0)?mQuestionBank.length-1:(mCurrentIndex+val) % mQuestionBank.length;

    }

}
