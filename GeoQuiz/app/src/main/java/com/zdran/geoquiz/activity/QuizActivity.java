package com.zdran.geoquiz.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.zdran.geoquiz.R;
import com.zdran.geoquiz.model.Question;

public class QuizActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;

    private TextView mTextView;
    private Button mNextButton;

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
        mNextButton = findViewById(R.id.next_button);

        updateQuestionText();
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuestionText();
            }
        });
    }

    /**
     * 更新问题
     */
    private void updateQuestionText() {
        mCurrentIndex = (mCurrentIndex + 1) % mQuestionsBank.length;
        int question = mQuestionsBank[mCurrentIndex].getTextResId();
        mTextView.setText(question);
    }

    /**
     * 检查问题是否正确
     * @param userPressedTrue 用户答案
     */
    private void checkAnswer(boolean userPressedTrue){
        if (userPressedTrue == mQuestionsBank[mCurrentIndex].isAnswerTrue()) {
            Toast.makeText(this, R.string.correct_toast, Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
        }
    }
}
