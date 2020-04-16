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

    private static final String EXTRA_ANSWER_SHOWN =
            "com.zdran.geoquiz.activity.answer_shown";

    private boolean mAnswerIsTrue;

    private TextView mAnswerTextView;

    /**
     * 创建 Intent
     * @param packageContext contex
     * @param answerIsTrue 答案是否为 true
     * @return
     */
    public static Intent newIntent(Context packageContext, boolean answerIsTrue) {
        Intent intent = new Intent(packageContext, CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return intent;
    }

    /**
     * 是否查看答案，供父 Activity 使用
     * @param intent
     * @return
     */
    public static boolean wasAnswerShow(Intent intent) {
        return intent.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);
        mAnswerIsTrue = super.getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        //显示答案
        Button showAnswerButton = findViewById(R.id.show_answer_button);
        mAnswerTextView = findViewById(R.id.answer_text_view);

        showAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAnswerIsTrue) {
                    mAnswerTextView.setText(R.string.true_button);
                } else {
                    mAnswerTextView.setText(R.string.false_button);
                }
                setShowAnswerResult(true);
            }
        });

    }

    /**
     * 返回给父 Activity 的数据
     * @param isShowAnswer 是否查看答案
     */
    private void setShowAnswerResult(boolean isShowAnswer) {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isShowAnswer);
        setResult(RESULT_OK, data);
    }
}
