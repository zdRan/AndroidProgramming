package com.zdran.criminalintent.activity;

import androidx.fragment.app.Fragment;

import com.zdran.criminalintent.fragment.CrimeListFragment;

/**
 *
 * Create by Ranzd on 2020-04-19 19:36
 *
 * @author cm.zdran@foxmail.com
 */
public class CrimeListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }

}
