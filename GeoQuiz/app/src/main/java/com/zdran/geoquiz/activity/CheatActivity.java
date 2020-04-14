package com.zdran.geoquiz.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zdran.geoquiz.R;

/**
 * Create by Ranzd on 2020-04-11 22:31
 *
 * @author cm.zdran@foxmail.com
 */
public class CheatActivity extends AppCompatActivity {
    private static final String EXTRA_ANSWER_IS_TRUE =
            "com.zdran.geoquiz.activity.answer_is_true";

    private boolean mAnswerIsTrue;

    private Button mShowAnswerButton;
    private TextView mAnswerTextView;

    public static Intent newIntent(Context packageContext, boolean answerIsTrue) {
        Intent intent = new Intent(packageContext, CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        mAnswerIsTrue = super.getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        //显示答案
        mShowAnswerButton = findViewById(R.id.show_answer_button);
        mAnswerTextView = findViewById(R.id.answer_text_view);

        mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAnswerIsTrue) {
                    mAnswerTextView.setText(R.string.true_button);
                } else {
                    mAnswerTextView.setText(R.string.false_button);
                }

            }
        });

    }
}
