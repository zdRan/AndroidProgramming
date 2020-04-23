package com.zdran.criminalintent.activity;

import android.content.Context;
import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.zdran.criminalintent.fragment.DatePickerFragment;

import java.util.Date;

/**
 * Create by Ranzd on 2020-04-23 14:48
 *
 * @author cm.zdran@foxmail.com
 */
public class DatePickerDialogActivity extends SingleFragmentActivity {
    private static final String DIALOG_DATE = "dialogDate";

    public static Intent newIntent(Context context, Date date) {
        Intent intent = new Intent(context, DatePickerDialogActivity.class);
        intent.putExtra(DIALOG_DATE, date);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        Date date = (Date) getIntent().getSerializableExtra(DIALOG_DATE);
        return DatePickerFragment.newInstance(date);
    }

}
