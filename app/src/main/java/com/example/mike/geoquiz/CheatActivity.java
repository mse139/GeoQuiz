package com.example.mike.geoquiz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.util.Log;

import org.w3c.dom.Text;


public class CheatActivity extends AppCompatActivity {
    private static final String EXTRA_ANSWER_IS_TRUE = "com.example.mike.geoquiz.answer_is_true";
    private boolean mAnswerIstrue;
    private TextView mAnswerTextView;
    private Button  mShowAnswerButton;
    private static final String EXTRA_ANSWER_SHOWN = "com.example.mike.geoquiz.answer_shown";
    private boolean isAnswerShown;
    private static final String TAG = "CheatActivity";
    private static final String CHEATER_KEY = "cheater";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cheat);
        mAnswerIstrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE,false);

        mAnswerTextView = (TextView) findViewById(R.id.answer_text_view);
        mShowAnswerButton = (Button) findViewById(R.id.show_answer_button);
        // restore the cheating flag
        if(savedInstanceState != null ) {
            Log.d(TAG,"restoring saved instance");
            isAnswerShown = savedInstanceState.getInt(CHEATER_KEY,0) == 1 ;

            Log.d(TAG,"cheater is " + isAnswerShown);
            if (isAnswerShown) {
                if (mAnswerIstrue) {
                    mAnswerTextView.setText(R.string.true_button);
                }else {
                    mAnswerTextView.setText(R.string.false_button);
                }
                isAnswerShown = true;
                setAnswerShownResult();
            }

        }



        mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAnswerIstrue) {
                    mAnswerTextView.setText(R.string.true_button);
                }else {
                    mAnswerTextView.setText(R.string.false_button);
                }
                isAnswerShown = true;
                setAnswerShownResult();
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    int cx = mShowAnswerButton.getWidth()/2;
                    int cy = mShowAnswerButton.getHeight()/2;
                    float radius = mShowAnswerButton.getWidth();
                    Animator anim = ViewAnimationUtils.createCircularReveal(mShowAnswerButton,cx,cy,radius,0);
                    anim.addListener(new AnimatorListenerAdapter(){
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            mShowAnswerButton.setVisibility(View.INVISIBLE);
                        }
                    });
                    anim.start();
                } else {
                    mShowAnswerButton.setVisibility(View.INVISIBLE);
                }

            }
        });
    }

private void setAnswerShownResult( ) {
    Intent data = new Intent();
    data.putExtra(EXTRA_ANSWER_SHOWN,isAnswerShown);
    setResult(RESULT_OK,data);
}

    public static Intent newIntent(Context packageContext, boolean answerIsTrue) {
        Intent intent = new Intent(packageContext,CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE,answerIsTrue);
        return intent;
    }

    public static boolean wasAnswerShown(Intent result) {
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN,false);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG,"onSaveInstanceState");
        savedInstanceState.putInt(CHEATER_KEY,isAnswerShown ?1:0);
    }

}
