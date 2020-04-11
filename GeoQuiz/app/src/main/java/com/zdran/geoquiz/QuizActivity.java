package com.zdran.geoquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;

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
                //点击后执行
                Toast.makeText(QuizActivity.this, R.string.correct_toast,
                        Toast.LENGTH_SHORT).show();
            }
        });
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击后执行
                Toast toast = Toast.makeText(QuizActivity.this, R.string.incorrect_toast,
                        Toast.LENGTH_SHORT);
                //挑战联想，顶部Toast
                toast.setGravity(Gravity.TOP,0,0);
                toast.show();
            }
        });
    }
}
