package com.zdran.geoquiz.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.zdran.geoquiz.R;
import com.zdran.geoquiz.model.Question;

public class QuizActivity extends AppCompatActivity {
    private static final String TAG = "QuizActivity";

    private Button mTrueButton;
    private Button mFalseButton;

    private TextView mTextView;
    private ImageButton mNextButton;
    private ImageButton mPrevButton;

    private Question[] mQuestionsBank = new Question[]{
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };

    private int mCurrentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate: 方法执行");

        setContentView(R.layout.activity_main);
        setTitle(R.string.app_name);
        //获取组件
        mTrueButton = findViewById(R.id.true_button);
        mFalseButton = findViewById(R.id.false_button);

        //设置监听器
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        //增加 next 按钮
        mTextView = findViewById(R.id.question_text_view);
        updateQuestionText(0);
        //下一题
        mNextButton = findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuestionText(1);
            }
        });
        //上一题
        mPrevButton = findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuestionText(-1);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: 方法执行");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: 方法执行");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: 方法执行");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: 方法执行");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: 方法执行");
    }

    /**
     * 更新问题
     */
    private void updateQuestionText(int stepNumber) {
        mCurrentIndex = (mCurrentIndex + stepNumber) % mQuestionsBank.length;
        if (mCurrentIndex < 0) {
            mCurrentIndex = mQuestionsBank.length - 1;
        }
        int question = mQuestionsBank[mCurrentIndex].getTextResId();
        mTextView.setText(question);
    }

    /**
     * 检查问题是否正确
     *
     * @param userPressedTrue 用户答案
     */
    private void checkAnswer(boolean userPressedTrue) {
        if (userPressedTrue == mQuestionsBank[mCurrentIndex].isAnswerTrue()) {
            Toast.makeText(this, R.string.correct_toast, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
        }
    }
}
