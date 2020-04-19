package com.zdran.criminalintent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.zdran.criminalintent.fragment.CrimeFragment;

import java.util.Objects;

/**
 * Create by Ranzd on 2020-04-17 22:06
 *
 * @author cm.zdran@foxmail.com
 */
public class CrimeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime);
        FragmentManager fm = getSupportFragmentManager();
        Fragment crimeFragment = fm.findFragmentById(R.id.fragment_container);
        if (Objects.isNull(crimeFragment)) {
            crimeFragment = new CrimeFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, crimeFragment)
                    .commit();
        }
    }
}
