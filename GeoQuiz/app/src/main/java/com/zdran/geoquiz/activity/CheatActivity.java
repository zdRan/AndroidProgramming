package com.zdran.geoquiz.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.zdran.geoquiz.R;

import java.util.Objects;

/**
 * Create by Ranzd on 2020-04-11 22:31
 *
 * @author cm.zdran@foxmail.com
 */
public class CheatActivity extends AppCompatActivity {
    private static final String TAG = "CheatActivity";
    private static final String EXTRA_ANSWER_IS_TRUE =
            "com.zdran.geoquiz.activity.answer_is_true";

    private static final String EXTRA_ANSWER_SHOWN =
            "com.zdran.geoquiz.activity.answer_shown";

    private static final String KEY_SHOW_ANSWER = "showAnswer";
    private boolean mAnswerIsTrue;

    private TextView mAnswerTextView;

    private TextView mBuildVersionTextView;

    private Button mShowAnswerButton;
    /**
     * 是否查看了答案，解决屏幕旋转的问题
     */
    private boolean mIsShowAnswer;

    /**
     * 创建 Intent
     *
     * @param packageContext contex
     * @param answerIsTrue   答案是否为 true
     * @return
     */
    public static Intent newIntent(Context packageContext, boolean answerIsTrue) {
        Intent intent = new Intent(packageContext, CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return intent;
    }

    /**
     * 是否查看答案，供父 Activity 使用
     *
     * @param intent
     * @return
     */
    public static boolean wasAnswerShow(Intent intent) {
        boolean data = intent.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
        Log.d(TAG, "wasAnswerShow: " + data);
        return data;

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
                mIsShowAnswer = true;
                //低版本不显示动画
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    showAnimator();
                } else {
                    mShowAnswerButton.setVisibility(View.INVISIBLE);
                }
            }
        });

        if (!Objects.isNull(savedInstanceState)) {
            mIsShowAnswer = savedInstanceState.getBoolean(KEY_SHOW_ANSWER, false);
            //旋转屏幕后，回显答案
            if (mIsShowAnswer) {
                if (mAnswerIsTrue) {
                    mAnswerTextView.setText(R.string.true_button);
                } else {
                    mAnswerTextView.setText(R.string.false_button);
                }
            }
        }
        //显示版本号
        mBuildVersionTextView = findViewById(R.id.build_version_text);
        mBuildVersionTextView.setText(String.valueOf(Build.VERSION.SDK_INT));
    }

    /**
     * 解决当前屏幕旋转后，清除了作弊痕迹
     *
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_SHOW_ANSWER, mIsShowAnswer);
    }

    /**
     * 一定要在 super.finish(); 之前 setResult。
     */
    @Override
    public void finish() {
        Log.d(TAG, "finish: 执行！！");
        setShowAnswerResult(mIsShowAnswer);
        super.finish();
    }

    /**
     * 返回给父 Activity 的数据
     *
     * @param isShowAnswer 是否查看答案
     */
    private void setShowAnswerResult(boolean isShowAnswer) {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isShowAnswer);
        setResult(RESULT_OK, data);
    }

    /**
     * 添加动画
     */
    private void showAnimator() {
        int cx = mShowAnswerButton.getWidth() / 2;
        int cy = mShowAnswerButton.getHeight() / 2;
        float radius = mShowAnswerButton.getWidth();
        Animator animator = ViewAnimationUtils.createCircularReveal(mShowAnswerButton, cx, cy, radius, 0);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mShowAnswerButton.setVisibility(View.INVISIBLE);
            }
        });
        animator.start();
    }
}
