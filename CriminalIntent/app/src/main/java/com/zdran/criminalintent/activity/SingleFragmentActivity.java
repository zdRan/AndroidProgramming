package com.zdran.criminalintent.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.zdran.criminalintent.R;

import java.util.Objects;

/**
 * Create by Ranzd on 2020-04-19 19:23
 *
 * @author cm.zdran@foxmail.com
 */
public abstract class SingleFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
        FragmentManager fm = getSupportFragmentManager();
        Fragment crimeFragment = fm.findFragmentById(R.id.fragment_container);

        if (Objects.isNull(crimeFragment)) {
            crimeFragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, crimeFragment)
                    .commit();
        }
    }

    /**
     * 由子类指定需要生成的 Fragment
     *
     * @return
     */
    protected abstract Fragment createFragment();

}
