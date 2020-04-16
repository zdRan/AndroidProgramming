package com.zdran.geoquiz.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
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
    private static final String KEY_CHEAT = "cheat";
    private static final String KEY_CORRECT_COUNT = "correctCount";
    private static final String KEY_INCORRECT_COUNT = "incorrectCount";
    private static final String KEY_USED = "used";

    //查看答案的 Activity 请求码
    private static final int REQUEST_CODE_CHEAT = 0;

    /**
     * 问题文本
     */
    private TextView mTextView;

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
     * 每个问题是否作弊
     */
    private boolean[] mIsCheaterArray = new boolean[mQuestionsBank.length];
    /**
     * 每个问题是否回答过
     */
    private boolean[] mIsUsed = new boolean[mQuestionsBank.length];
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
            mCorrectCount = savedInstanceState.getInt(KEY_CORRECT_COUNT, 0);
            mIncorrectCount = savedInstanceState.getInt(KEY_INCORRECT_COUNT, 0);
            mIsCheaterArray = savedInstanceState.getBooleanArray(KEY_CHEAT);
            mIsUsed = savedInstanceState.getBooleanArray(KEY_USED);
        }
        Log.d(TAG, "onCreate: 方法执行");

        setContentView(R.layout.activity_main);
        setTitle(R.string.app_name);
        //获取组件
        Button trueButton = findViewById(R.id.true_button);
        Button falseButton = findViewById(R.id.false_button);

        //设置监听器
        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });
        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });


        mTextView = findViewById(R.id.question_text_view);

        updateQuestionText(0);
        //下一题
        ImageButton nextButton = findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateQuestionText(1);
            }
        });
        //上一题
        ImageButton prevButton = findViewById(R.id.prev_button);
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuestionText(-1);
            }
        });

        //查看答案
        Button cheatButton = findViewById(R.id.cheat_button);
        cheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean answer = mQuestionsBank[mCurrentIndex].isAnswerTrue();
                Intent intent = CheatActivity.newIntent(QuizActivity.this, answer);
                startActivityForResult(intent, REQUEST_CODE_CHEAT);
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
        outState.putInt(KEY_CORRECT_COUNT, mCorrectCount);
        outState.putInt(KEY_INCORRECT_COUNT, mIncorrectCount);
        outState.putBooleanArray(KEY_CHEAT, mIsCheaterArray);
        outState.putBooleanArray(KEY_USED, mIsUsed);

    }

    /**
     * 处理子 Activity 的返回结果
     *
     * @param requestCode 请求参数时原始到的 请求代码
     * @param resultCode  返回结果里的返回代码
     * @param data        返回的数据
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode != REQUEST_CODE_CHEAT) {
            return;
        }
        if (resultCode != RESULT_OK) {
            return;
        }
        if (data != null) {
            mIsCheaterArray[mCurrentIndex] = CheatActivity.wasAnswerShow(data);
        }
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
        if (mIsUsed[mCurrentIndex]) {
            Log.i(TAG, "onClick: 已经回答过该题！");
            return;
        }
        //如果查看答案，认为答错
        if (mIsCheaterArray[mCurrentIndex]) {
            Toast.makeText(this, R.string.judgment_toast, Toast.LENGTH_SHORT).show();
            mIncorrectCount++;
        } else {
            if (userPressedTrue == mQuestionsBank[mCurrentIndex].isAnswerTrue()) {
                Toast.makeText(this, R.string.correct_toast, Toast.LENGTH_SHORT).show();
                mCorrectCount++;
            } else {
                Toast.makeText(this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
                mIncorrectCount++;
            }
        }
        mIsUsed[mCurrentIndex] = true;
        if (mCorrectCount + mIncorrectCount == mQuestionsBank.length) {
            Toast.makeText(this,
                    "游戏结束！得分！" + Math.rint(mCorrectCount * 100.0 / mQuestionsBank.length * 100.0) / 100,
                    Toast.LENGTH_LONG).show();
        }
    }
}
