package com.zdran.criminalintent.activity;

import androidx.fragment.app.Fragment;

import com.zdran.criminalintent.fragment.CrimeFragment;

/**
 * Create by Ranzd on 2020-04-17 22:06
 *
 * @author cm.zdran@foxmail.com
 */
public class CrimeActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new CrimeFragment();
    }
}
