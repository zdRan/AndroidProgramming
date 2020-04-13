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

/**
 * Create by Ranzd on 2020-04-11 22:31
 *
 * @author cm.zdran@foxmail.com
 */
public class QuizActivity extends AppCompatActivity {
    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private Button mTrueButton;
    private Button mFalseButton;

    private TextView mTextView;
    private ImageButton mNextButton;
    private ImageButton mPrevButton;
    /**
     * 问题列表
     */
    private Question[] mQuestionsBank = new Question[]{
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };
    /**
     * 当前问题的下标
     */
    private int mCurrentIndex = 0;
    /**
     * 用户回答正确的问题数量
     */
    private int mCorrectCount = 0;
    /**
     * 用户回答错误的问题数量
     */
    private int mIncorrectCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //屏幕旋转后重新加载问题
        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState: 方法执行");
        //Activity 销毁前保存问题下标
        outState.putInt(KEY_INDEX, mCurrentIndex);
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
        //已经回答过的问题不能再次回答
        if (mQuestionsBank[mCurrentIndex].isUsed()) {
            Log.i(TAG, "onClick: 已经回答过该题！");
            return;
        }

        if (userPressedTrue == mQuestionsBank[mCurrentIndex].isAnswerTrue()) {
            Toast.makeText(this, R.string.correct_toast, Toast.LENGTH_SHORT).show();
            mCorrectCount++;
        } else {
            Toast.makeText(this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
            mIncorrectCount++;
        }
        mQuestionsBank[mCurrentIndex].setUsed(true);

        if (mCorrectCount + mIncorrectCount == mQuestionsBank.length) {
            Toast.makeText(this,
                    "游戏结束！得分！" + Math.rint(mCorrectCount * 100.0 / mQuestionsBank.length * 100.0) / 100,
                    Toast.LENGTH_LONG).show();
        }
    }
}
